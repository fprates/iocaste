package org.iocaste.calendar;

import java.util.Date;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Calendar;

public class Context extends AbstractContext {
    public CalendarData calendardata;
    public Calendar control;
    public Date date;
    
    public Context() {
        calendardata = new CalendarData();
    }
}
