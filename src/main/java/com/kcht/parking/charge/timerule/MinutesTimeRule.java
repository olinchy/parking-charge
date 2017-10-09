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
        int minutes = timeSections.stream().map(TimeSection::minutes).reduce((x, y) -> x + y).get();
        int counts = minutes / ratio;
        if (minutes % ratio > 0) {
            counts += 1;
        }
        return counts;
    }
}
