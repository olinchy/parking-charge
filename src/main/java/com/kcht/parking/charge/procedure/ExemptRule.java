package com.kcht.parking.charge.procedure;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.tools.LambdaConverter;
import com.kcht.parking.charge.tools.LambdaReducer;
import com.kcht.parking.charge.tools.To;

@SuppressWarnings("ALL")
public class ExemptRule implements Rule {
    public ExemptRule(final Integer limit) {
        this.limit = limit;
    }

    private final Integer limit;
    private List<TimeSection> timeSectionList;

    @Override
    public double charge() {
        return 0;
    }

    @Override
    public void process(final List<TimeSection> timeSectionList) {
        this.timeSectionList = timeSectionList;
    }

    @Override
    public boolean ignoreOthers() {
        return To.reduce(To.map(timeSectionList, new LambdaConverter<Integer, TimeSection>() {
            @Override
            public Integer to(final TimeSection content) {
                return content.minutes();
            }
        }), new LambdaReducer<Integer>() {
            @Override
            public Integer reduce(final Integer x, final Integer y) {
                return x + y;
            }
        }) < limit;
    }
}
