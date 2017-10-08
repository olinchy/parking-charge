package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

/**
 * Created by olinchy on 04/10/2017.
 */
public class MaxHourTimeRule implements TimeRule {
    public MaxHourTimeRule(final int maxHour, final int step) {
        this.maxHour = maxHour;
        this.step = step;
    }

    private int maxHour;
    private int step;

    @Override
    public int count(final List<TimeSection> timeSections) {
        int toReturn = 0;
        for (TimeSection timeSection : timeSections) {
            int hours = countHour(timeSection);
            toReturn += countMaxOut(hours);
        }
        return toReturn;
    }

    private int countHour(final TimeSection timeSection) {
        int minutes = timeSection.minutes();
        int hours = minutes / 60;
        if (minutes % 60 > 0) {
            hours++;
        }
        return hours;
    }

    private int countMaxOut(final int hours) {
        int toReturn = 0;
        int toMinus = hours;
        while (toMinus > 0) {
            if (toMinus >= maxHour) {
                toReturn += maxHour;
            } else {
                toReturn += toMinus;
            }
            toMinus -= step;
        }
        return toReturn;
    }
}
