package org.iocaste.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.iocaste.shell.common.Button;
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

public class Response {
    
    private static final void createCalendarLink(Container container,
            Context context, String name) {
        PopupControl control;
        Link link;
        String onclick, form, action;
        
        form = context.function.getParameter("form");
        action = context.function.getParameter("action");
        onclick = new StringBuilder("formSubmit('").
                append(form).append("', '").
                append(action).append("', '").
                append(name).append("');").toString();
        
        control = context.control.getView().getElement(name);
        link = new Link(container, "link_".concat(name), null);
        link.setText(control.getText());
        link.setStyleClass("month");
        link.setEvent("click", onclick);
    }
    
    public static final void main(Context context) throws Exception {
        int weekday;
        Date date;
        DateFormat format, formatdest;
        String compname, action, value;
        StandardContainer container;
        Link link;
        Text text;
        Table table;
        TableItem item;
        StyleSheet stylesheet;
        Locale locale;
        Map<String, String> style;
        Map<Integer, String> constants;
        
        stylesheet = context.view.styleSheetInstance();
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
        
        style = stylesheet.clone(".calkey:link", ".link:link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "normal");
        style.put("display", "block");
        style = stylesheet.clone(".calkey:hover", ".calkey:link");
        style = stylesheet.clone(".calkey:visited", ".calkey:link");
        style = stylesheet.clone(".calkey:active", ".calkey:link");
        
        style = stylesheet.clone(".today:link", ".link:link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "bold");
        style.put("display", "block");
        style = stylesheet.clone(".today:hover", ".today:link");
        style = stylesheet.clone(".today:visited", ".today:link");
        style = stylesheet.clone(".today:active", ".today:link");
        
        style = stylesheet.clone(".month", ".text");
        style.put("display", "inline");
        style.put("text-decoration", "none");
        
        context.calendardata.update(context.date);
        container = new StandardContainer(context.view, "calstdcnt");
        container.setStyleClass("calcnt");
        
        createCalendarLink(container, context, context.control.getEarly());

        locale = context.view.getLocale();
        format = new SimpleDateFormat("d MMMMM yyyy", locale);
        value = format.format(context.date);
        
        text = new Text(container, "calhead");
        text.setText(value);
        text.setStyleClass("month");

        createCalendarLink(container, context, context.control.getLate());
        
        new Parameter(container, "value");
        
        table = new Table(container, "calendar");
        for (String name : context.calendardata.sweekdays)
            new TableColumn(table, name);
        
        item = new TableItem(table);
        if (context.calendardata.weekday > 1)
            for (int i = 0; i < (context.calendardata.weekday - 1); i++) {
                text = new Text(item,
                        new StringBuilder("caldummy").append(i).toString());
                text.setText("");
                text.setStyleClass("calkey");
            }
        format = new SimpleDateFormat("yyyy-M-d");
        formatdest = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        for (int day = 1; day <= context.calendardata.lastday; day++) {
            weekday = context.calendardata.weekday - 1;
            compname = new StringBuilder(
                    context.calendardata.sweekdays[weekday]).
                    append(day).toString();
            
            date = format.parse(new StringBuilder().
                    append(context.calendardata.year).
                    append("-").
                    append(context.calendardata.month + 1).
                    append("-").
                    append(day).toString());
            
            value = formatdest.format(date);
            action = new StringBuilder("javascript:setFieldCal('").
                    append(context.control.getInputName()).
                    append("','").
                    append(value).
                    append("')").toString();
            
            link = new Link(item, compname, action);
            link.setText(Integer.toString(day));
            link.setAbsolute(true);
            if (context.calendardata.today == day)
                link.setStyleClass("today");
            else
                link.setStyleClass("calkey");
            
            if (context.calendardata.weekday ==
                    context.calendardata.weekdays.length) {
                context.calendardata.weekday = context.calendardata.first;
                item = new TableItem(table);
                continue;
            }
            
            context.calendardata.weekday++;
        }

        new Button(container, "cancel").
                setEvent("click","closeCal()");
    }
}
