package com.kcht.parking.charge.charger;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timerule.TimeRule;

public class HeadTailCharger implements ChargerRule {
    public HeadTailCharger(final double headPrice, final double tailPrice, TimeRule timeRule) {
        this.tailPrice = tailPrice;
        this.headPrice = headPrice;
        this.timeRule = timeRule;
    }

    private final TimeRule timeRule;
    private double tailPrice;
    private double headPrice;

    @Override
    public double charge(final List<TimeSection> timeSections) {
        int newCount = timeRule.count(timeSections);
        return headPrice + (newCount - 1) * tailPrice;
    }
}
