package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.datastructure.Pair;
import com.kcht.parking.charge.timeline.TimeSection;

import static com.kcht.parking.charge.datastructure.Pair.pair;

/**
 * Created by olinchy on 04/10/2017.
 */
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

    @Override
    public int count(final List<TimeSection> timeSections) {
        int toReturn = timeSections.stream().map(
                timeSection -> pair(timeSection.minutes() / 60, timeSection.minutes() % 60))
                .map(pair -> pair(countMaxOut(pair.first()), getMinutes(pair)))
                .map(pair -> countChargeUnits(pair.first() * 60 + pair.second()))
                .reduce((x, y) -> x + y).get();

        return toReturn;
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
