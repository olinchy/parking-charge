package com.kcht.parking.charge.charger;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

/**
 * Created by olinchy on 04/10/2017.
 */
public interface ChargerRule {
    double charge(final List<TimeSection> timeSections);
}
