package com.kcht.parking.charge.procedure;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

/**
 * Created by olinchy on 04/10/2017.
 */
@SuppressWarnings("ALL")
public class ExemptRule implements Rule {
    public ExemptRule(final Integer limit) {
        this.limit = limit;
    }

    private final Integer limit;
    private boolean ignore = false;

    @Override
    public double charge() {
        return 0;
    }

    @Override
    public void process(final List<TimeSection> timeSectionList) {
        int minutes = timeSectionList.stream().map(TimeSection::minutes).reduce((x, y) -> x + y).get();
        if (minutes < limit) {
            this.ignore = true;
        }
    }

    @Override
    public boolean ignoreOthers() {
        return ignore;
    }
}
