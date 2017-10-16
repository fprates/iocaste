package org.iocaste.kernel.runtime.shell.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.PopupData;

public class CalendarData {
    public PopupData popup;
    public org.iocaste.shell.common.Calendar control;
    public int lastday, weekday, today, first, month, year;
    public int[] weekdays;
    public String[] sweekdays;
    public Map<Integer, String> monthtext;
    private Calendar calendar;

    public CalendarData() {
        calendar = Calendar.getInstance();
        weekdays = new int[] {
                Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY
        };
        
        sweekdays = new String[] {
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday"};
        
        monthtext = new HashMap<>();
        monthtext.put(Calendar.JANUARY, "Janeiro");
        monthtext.put(Calendar.FEBRUARY, "Fevereiro");
        monthtext.put(Calendar.MARCH, "Março");
        monthtext.put(Calendar.APRIL, "Abril");
        monthtext.put(Calendar.MAY, "Maio");
        monthtext.put(Calendar.JUNE, "Junho");
        monthtext.put(Calendar.JULY, "Julho");
        monthtext.put(Calendar.AUGUST, "Agosto");
        monthtext.put(Calendar.SEPTEMBER, "Setembro");
        monthtext.put(Calendar.OCTOBER, "Outubro");
        monthtext.put(Calendar.NOVEMBER, "Novembro");
        monthtext.put(Calendar.DECEMBER, "Dezembro");
    }
    
    public final Date calculate(Date date, int amount) {
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }
    
    public final Date getTime() {
        return calendar.getTime();
    }
    
    public final void update(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        weekday = calendar.get(Calendar.DAY_OF_WEEK);
        
        calendar.setTime(date);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        first = Calendar.SUNDAY;
    }
}
