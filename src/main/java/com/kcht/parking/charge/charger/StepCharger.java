package com.kcht.parking.charge.charger;

import java.util.List;

import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timerule.TimeRule;

public class StepCharger implements ChargerRule {
    public StepCharger(final double stepPrice, TimeRule timeRule) {
        this.stepPrice = stepPrice;
        this.timeRule = timeRule;
    }

    private final TimeRule timeRule;
    private double stepPrice;

    @Override
    public double charge(final List<TimeSection> timeSections) {
        return stepPrice * timeRule.count(timeSections);
    }
}
