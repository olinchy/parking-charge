package com.kcht.parking.charge.procedure;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

public interface Rule {
    double charge();

    void process(List<TimeSection> timeSectionList);

    boolean ignoreOthers();
}
