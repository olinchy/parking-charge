package com.kcht.parking.charge.timerule;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

/**
 * Created by olinchy on 04/10/2017.
 */
public class OnceTimeRule implements TimeRule {
    @Override
    public int count(final List<TimeSection> timeSections) {
        return timeSections.size();
    }
}
