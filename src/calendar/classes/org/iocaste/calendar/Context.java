package org.iocaste.calendar;

import org.iocaste.shell.common.AbstractContext;

public class Context extends AbstractContext {
    public CalendarData calendardata;
    
    public Context() {
        calendardata = new CalendarData();
    }
}
