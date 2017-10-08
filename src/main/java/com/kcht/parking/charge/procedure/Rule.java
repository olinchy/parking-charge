package com.kcht.parking.charge.procedure;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

/**
 * Created by olinchy on 04/10/2017.
 */
public interface Rule {
    double charge();

    void process(List<TimeSection> timeSectionList);

    boolean ignoreOthers();
}
