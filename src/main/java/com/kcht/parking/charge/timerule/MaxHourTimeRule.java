package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.datastructure.Pair;
import com.kcht.parking.charge.timeline.TimeSection;

import static com.kcht.parking.charge.datastructure.Pair.pair;

public class MaxHourTimeRule implements TimeRule {
    public MaxHourTimeRule(Integer... numerics) {
        this.maxHour = numerics[0];
        this.step = numerics[1];
        if (numerics.length == 2) {
            this.unit = 60;
        } else {
            this.unit = numerics[2];
        }
    }

    private final int unit;
    private int maxHour;
    private int step;

    /**
     * sum all minutes first, and then count hours
     *
     * @param timeSections
     */
    @Override
    public int count(final List<TimeSection> timeSections) {
        int result = timeSections.stream().map(
                // every section : hours, minutes
                timeSection -> pair(timeSection.minutes() / 60, timeSection.minutes() % 60))
                // every section : hours according [maxHour, step], minutes(= 0 when maxHour < hours < step)
                .map(pair -> pair(countMaxOut(pair.first()), getMinutes(pair)))
                // every section : to minutes
                .map(pair -> pair.first() * 60 + pair.second())
                // sum
                .reduce((x, y) -> x + y).get();

        // minutes / unit
        return countChargeUnits(result);
    }

    //    /**
    //     * count every timesection individually
    //     *
    //     * @param timeSections
    //     */
    //    @Override
    //    public int count(final List<TimeSection> timeSections) {
    //        int result = timeSections.stream().map(
    //                timeSection -> pair(timeSection.minutes() / 60, timeSection.minutes() % 60))
    //                .map(pair -> pair(countMaxOut(pair.first()), getMinutes(pair)))
    //                .map(pair -> countChargeUnits(pair.first() * 60 + pair.second()))
    //                .reduce((x, y) -> x + y).get();
    //
    //        return result;
    //    }

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

    private int getMinutes(final Pair<Integer, Integer> pair) {
        int min = maxHour;
        int max = step;
        int minutes = pair.second();
        while (pair.first() >= min) {
            if (pair.first() >= min && pair.first() < max) {
                minutes = 0;
                break;
            }
            min += max;
            max += max;
        }
        return minutes;
    }

    private int countChargeUnits(int minutes) {
        int hours = minutes / unit;
        if (minutes % unit > 0) {
            hours++;
        }
        return hours;
    }
}
