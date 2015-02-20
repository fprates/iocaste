package org.iocaste.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarData {
    public int lastday, weekday, today, first, month, year;
    public int[] weekdays;
    public String[] sweekdays;
    public Date date;
    public Map<Integer, String> monthtext;

    public CalendarData() {
        Calendar calendar;
        
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
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
        
        weekday = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        first = Calendar.SUNDAY;
        
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
}
