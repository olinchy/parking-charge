package com.kcht.parking.charge.timeline;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Period {
    public Period(String period) {
        if (!period.matches("[0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2}")) {
            throw new IllegalArgumentException("period must like 07:00-22:00");
        }
        String start = period.split("-")[0];
        String end = period.split("-")[1];

        this.startHour = Integer.parseInt(start.split(":")[0]);
        this.startMinute = Integer.parseInt(start.split(":")[1]);

        this.endHour = Integer.parseInt(end.split(":")[0]);
        this.endMinute = Integer.parseInt(end.split(":")[1]);

        if (startHour > 24 || startHour < 0 || endHour > 24 || endHour < 0 || startMinute > 60 || endMinute > 60) {
            throw new IllegalArgumentException(period);
        }

        if (endHour < startHour) {
            this.dayStep = 1;
        }
    }

    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;
    private int dayStep = 0;

    public boolean has(final Date next) {
        Date startDate = getStartDate(next);
        Date endDate = getEndDate(startDate);

        return startDate.getTime() <= next.getTime() && endDate.after(next);
    }

    private Date getStartDate(final Date next) {
        GregorianCalendar calendar = getCalender(next, startHour, startMinute);
        if (calendar.getTime().after(next)) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        }
        return calendar.getTime();
    }

    private Date getEndDate(final Date next) {
        GregorianCalendar calendar = getCalender(next, endHour, endMinute);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dayStep);
        return calendar.getTime();
    }

    private GregorianCalendar getCalender(final Date next, final int hour, final int minutes) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(next);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public Date nextSpot(final Date next) {
        return getEndDate(next);
    }

    public boolean connectWith(final Period another) {
        return this.endHour == another.startHour && this.endMinute == another.startMinute
                && this.startHour == another.endHour && this.startMinute == another.endMinute;
    }

    @Override
    public String toString() {
        return "Period{" + startHour + ":" + startMinute + "-" + endHour + ":" + endMinute;
    }
}
