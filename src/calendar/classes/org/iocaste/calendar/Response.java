package org.iocaste.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class Response {
    
    private static final void createCalendarLink(Container container,
            Context context, String name, String text) {
        Link link;
        String onclick, form, action;
        
        form = context.function.getParameter("form");
        action = context.function.getParameter("action");
        onclick = new StringBuilder("javascript:formSubmit('").
                append(form).append("', '").
                append(action).append("', '").
                append(name).append("');").toString();
        
        link = new Link(container, "link_".concat(name), onclick);
        link.setAbsolute(true);
        link.setText(text);
        link.setStyleClass("month");
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
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(".calcnt");
        stylesheet.put(".calcnt", "position", "absolute");
        stylesheet.put(".calcnt", "padding", "10px");
        stylesheet.put(".calcnt", "float", "right");
        stylesheet.put(".calcnt", "right", "0px");
        stylesheet.put(".calcnt", "overflow", "hidden");
        stylesheet.put(".calcnt", "background-color", "#ffffff");
        stylesheet.put(".calcnt", "border-style", "solid");
        stylesheet.put(".calcnt", "border-width", "2px");
        stylesheet.put(".calcnt", "border-color", "rgb(176, 176, 176)");
        
        stylesheet.newElement(".calkey");
        stylesheet.put(".calkey", "padding", "0px");
        stylesheet.put(".calkey", "margin", "0px");
        stylesheet.put(".calkey", "text-align", "middle");
        stylesheet.put(".calkey", "font-weight", "normal");
        stylesheet.put(".calkey", "display", "block");
        stylesheet.put(".calkey", "text-decoration", "none");
        
        stylesheet.newElement(".today");
        stylesheet.put(".today", "padding", "0px");
        stylesheet.put(".today", "margin", "0px");
        stylesheet.put(".today", "text-align", "middle");
        stylesheet.put(".today", "font-weight", "bold");
        stylesheet.put(".today", "display", "block");
        stylesheet.put(".today", "text-decoration", "none");
        
        stylesheet.newElement(".month");
        stylesheet.put(".month", "display", "inline");
        stylesheet.put(".month", "text-decoration", "none");
        
        context.calendardata.update(context.date);
        container = new StandardContainer(context.view, "calstdcnt");
        container.setStyleClass("calcnt");
        
        createCalendarLink(container, context, context.control.getEarly(), "<");

        locale = context.view.getLocale();
        format = new SimpleDateFormat("d MMMMM yyyy", locale);
        value = format.format(context.date);
        
        text = new Text(container, "calhead");
        text.setText(value);
        text.setStyleClass("month");

        createCalendarLink(container, context, context.control.getLate(), ">");
        
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
                addEvent("onClick","javascript:closeCal()");
    }
}
