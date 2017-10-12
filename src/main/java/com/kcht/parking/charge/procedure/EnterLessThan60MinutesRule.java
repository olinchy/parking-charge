package com.kcht.parking.charge.procedure;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kcht.parking.charge.charger.Charger;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.tools.LambdaConverter;
import com.kcht.parking.charge.tools.LambdaReducer;
import com.kcht.parking.charge.tools.To;

public class EnterLessThan60MinutesRule implements Rule {
    public EnterLessThan60MinutesRule(Charger charger) {
        this.charger = charger;
    }

    private boolean isIgnoreOthers = false;
    private double charge = 0;
    private Charger charger;

    @Override
    public double charge() {
        return this.charge;
    }

    @Override
    public void process(final List<TimeSection> timeSectionList) {
        int minutes = To.reduce(To.map(timeSectionList, new LambdaConverter<Integer, TimeSection>() {
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
        if (minutes <= 60) {
            this.isIgnoreOthers = true;
            TimeSection max = To.max(To.limit(timeSectionList, 2), new Comparator<TimeSection>() {
                @Override
                public int compare(final TimeSection o1, final TimeSection o2) {
                    return o1.minutes() > o2.minutes() ? 1 : -1;
                }
            });
            this.charge = charger.charge(max.type(), Collections.singletonList(max));
        }
    }

    @Override
    public boolean ignoreOthers() {
        return this.isIgnoreOthers;
    }
}
