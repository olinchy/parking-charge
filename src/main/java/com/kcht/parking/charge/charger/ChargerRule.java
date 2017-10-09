package com.kcht.parking.charge.charger;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;

public interface ChargerRule {
    double charge(final List<TimeSection> timeSections);
}
