package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.datastructure.Pair;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.tools.LambdaConverter;
import com.kcht.parking.charge.tools.LambdaReducer;
import com.kcht.parking.charge.tools.To;

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
        int result = To.reduce(
                To.map(To.map(To.map(timeSections, new LambdaConverter<Pair<Integer, Integer>, TimeSection>() {
                    // every section : hours, minutes
                    @Override
                    public Pair<Integer, Integer> to(final TimeSection timeSection) {
                        return pair(timeSection.minutes() / 60, timeSection.minutes() % 60);
                    }
                }), new LambdaConverter<Pair<Integer, Integer>, Pair<Integer, Integer>>() {
                    // every section : hours according [maxHour, step], minutes(= 0 when maxHour < hours < step)
                    @Override
                    public Pair<Integer, Integer> to(
                            final Pair<Integer, Integer> pair) {
                        return pair(countMaxOut(pair.first()), getMinutes(pair));
                    }
                }), new LambdaConverter<Integer, Pair<Integer, Integer>>() {
                    // every section : to minutes
                    @Override
                    public Integer to(final Pair<Integer, Integer> pair) {
                        return pair.first() * 60 + pair.second();
                    }
                }), new LambdaReducer<Integer>() {
                    // sum
                    @Override
                    public Integer reduce(final Integer x, final Integer y) {
                        return x + y;
                    }
                });

        // minutes / unit
        return countChargeUnits(result);
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
