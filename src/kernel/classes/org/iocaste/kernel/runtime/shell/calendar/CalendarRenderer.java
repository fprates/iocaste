package org.iocaste.kernel.runtime.shell.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.iocaste.kernel.runtime.shell.PopupData;
import org.iocaste.kernel.runtime.shell.PopupRenderer;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class CalendarRenderer implements PopupRenderer {
    private Messages messages;
    
    public CalendarRenderer() {
        messages = new Messages();
        messages.entries();
    }
    
    private final void createCalendarLink(Container container,
            CalendarData data, String name) {
        PopupControl control;
        Link link;
        String onclick;
        
        onclick = new StringBuilder("formSubmit('").
                append(data.popup.form).append("', '").
                append(data.popup.action).append("', '").
                append(name).append("');").toString();
        
        control = data.popup.viewctx.view.getElement(name);
        link = new Link(container, "link_".concat(name), null);
        link.setText(control.getText());
        link.setStyleClass("calmonth");
        link.setEvent("click", onclick);
    }
    
    private final void response(CalendarData data) throws Exception {
        int weekday;
        Date date;
        DateFormat format, formatdest;
        String compname, action, value;
        Link link;
        Text text;
        Table table;
        TableItem item;
        Locale locale;
        Properties texts;
        Button button;
        
        Style.execute(data);
        
        data.update(data.date);
        data.popup.container =
                new StandardContainer(data.popup.viewctx.view, "calstdcnt");
        data.popup.container.setStyleClass("calcnt");
        
        createCalendarLink(
                data.popup.container, data, data.control.getEarly());

        locale = data.popup.viewctx.view.getLocale();
        format = new SimpleDateFormat("d MMMMM yyyy", locale);
        value = format.format(data.date);
        
        text = new Text(data.popup.container, "calhead");
        text.setText(value);
        text.setStyleClass("caldate");

        createCalendarLink(data.popup.container, data, data.control.getLate());
        
        new Parameter(data.popup.container, "value");
        
        table = new Table(data.popup.container, "calendar");
        table.setStyleClass(Table.HEAD, "calthead");
        table.setStyleClass(Table.TABLE_CELL, "caltd");
        
        texts = messages.getMessages(data.popup.viewctx.locale);
        for (String name : data.sweekdays)
            new TableColumn(table, name).setText((String)texts.get(name));
        
        item = new TableItem(table);
        if (data.weekday > 1)
            for (int i = 0; i < (data.weekday - 1); i++) {
                text = new Text(item,
                        new StringBuilder("caldummy").append(i).toString());
                text.setText("");
                text.setStyleClass("calkey");
            }
        format = new SimpleDateFormat("yyyy-M-d");
        formatdest = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        for (int day = 1; day <= data.lastday; day++) {
            weekday = data.weekday - 1;
            compname = new StringBuilder(data.sweekdays[weekday]).
                    append(day).toString();
            
            date = format.parse(new StringBuilder().
                    append(data.year).
                    append("-").
                    append(data.month + 1).
                    append("-").
                    append(day).toString());
            
            value = formatdest.format(date);
            action = new StringBuilder("setFieldCal('").
                    append(data.control.getInputName()).
                    append("','").
                    append(value).
                    append("')").toString();
            
            link = new Link(item, compname, null);
            link.setText(Integer.toString(day));
            link.setEvent("click", action);
            if (data.today == day)
                link.setStyleClass("caltoday");
            else
                link.setStyleClass("calkey");
            
            if (data.weekday == data.weekdays.length) {
                data.weekday = data.first;
                item = new TableItem(table);
                continue;
            }
            
            data.weekday++;
        }

        button = new Button(data.popup.container, "cancel");
        button.setEvent("click","closeCal()");
        button.setText((String)texts.get("cancel"));
    }
    
    @Override
    public final void run(PopupData popupdata) throws Exception {
        Calendar calendar;
        byte mode;
        CalendarData data = new CalendarData();
        
        data.popup = popupdata;
        data.control = (Calendar)popupdata.control;
        mode = data.control.getMode();
        
        switch (mode) {
        case -1:
        case 1:
            data.control = data.popup.viewctx.view.
                    getElement(data.control.getMaster());
            data.date = data.calculate(data.control.getDate(), mode);
            break;
        default:
            data.date = new Date();
            break;
        }
        
        new Parameter(data.popup.viewctx.view, "p_".concat(data.control.getName())).
                set(data.date);
        
        calendar = data.popup.viewctx.view.getElement(data.control.getEarly());
        new Parameter(
                data.popup.viewctx.view, "p_".concat(calendar.getName()));
        
        calendar = data.popup.viewctx.view.getElement(data.control.getLate());
        new Parameter(
                data.popup.viewctx.view, "p_".concat(calendar.getName()));
        
        response(data);
    }
}


class Style {
    
    public static final void execute(CalendarData data) {
        StyleSheet stylesheet;
        Map<String, String> style;
        Map<Integer, String> constants;
        
        stylesheet = StyleSheet.instance(data.popup.viewctx.view);
        constants = stylesheet.getConstants();
        style = stylesheet.newElement(".calcnt");
        style.put("position", "absolute");
        style.put("padding", "10px");
        style.put("float", "left");
        style.put("overflow", "hidden");
        style.put("background-color", constants.get(Shell.BACKGROUND_COLOR));
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", constants.get(Shell.FRAME_COLOR));
        style.put("z-index", "1");
        style.put("margin-top", "2em");
        style.put("box-shadow", constants.get(Shell.SHADOW));
        
        style = stylesheet.clone(".calkey", ".link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "normal");
        style.put("display", "block");
        style = stylesheet.clone(".calkey:hover", ".calkey");
        
        style = stylesheet.clone(".caltoday", ".link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "bold");
        style.put("display", "block");
        style = stylesheet.clone(".caltoday:hover", ".caltoday");
        
        style = stylesheet.clone(".caldate", ".text");
        style.put("display", "inline");
        style.put("text-decoration", "none");

        style = stylesheet.clone(".calmonth", ".caldate");
        style.put("color", constants.get(Shell.CLICKABLE_COLOR));
        style.put("cursor", "pointer");

        style = stylesheet.clone(".calthead", ".table_head");
        style.put("display", "table-header-group");
        
        style = stylesheet.clone(".caltd", ".table_cell");
        style.put("display", "table-cell");
        style.put("float", "unset");
        style.remove("width");
    }
}
