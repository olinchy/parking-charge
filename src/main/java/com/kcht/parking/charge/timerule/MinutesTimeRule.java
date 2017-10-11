package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

public class MinutesTimeRule implements TimeRule {
    public MinutesTimeRule(int perMinutes) {
        ratio = perMinutes;
    }

    private final int ratio;

    @Override
    public int count(final List<TimeSection> timeSections) {
        int count = timeSections.stream().map(timeSection -> count(timeSection.minutes())).reduce(
                (x, y) -> x + y).get();
        return count;
    }

    private int count(final int minutes) {
        int counts = minutes / ratio;
        if (minutes % ratio > 0) {
            counts += 1;
        }
        return counts;
    }
}
