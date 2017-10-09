package com.kcht.parking.charge.procedure;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

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
        return timeSectionList.stream().map(TimeSection::minutes).reduce((x, y) -> x + y).get() < limit;
    }
}
