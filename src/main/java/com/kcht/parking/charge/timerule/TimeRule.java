package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;


public interface TimeRule {
    int count(List<TimeSection> timeSections);
}
