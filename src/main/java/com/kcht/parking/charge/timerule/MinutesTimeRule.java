package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.tools.LambdaConverter;
import com.kcht.parking.charge.tools.LambdaReducer;
import com.kcht.parking.charge.tools.To;

public class MinutesTimeRule implements TimeRule {
    public MinutesTimeRule(int perMinutes) {
        ratio = perMinutes;
    }

    private final int ratio;

    @Override
    public int count(final List<TimeSection> timeSections) {
        int minutes = To.reduce(To.map(timeSections, new LambdaConverter<Integer, TimeSection>() {
            @Override
            public Integer to(final TimeSection content) {
                return content.minutes();
            }
        }), new LambdaReducer<Integer>() {
            @Override
            public Integer reduce(final Integer x, final Integer y) {
                return x + y;
            }
        });
        int counts = minutes / ratio;
        if (minutes % ratio > 0) {
            counts += 1;
        }
        return counts;
    }
}
