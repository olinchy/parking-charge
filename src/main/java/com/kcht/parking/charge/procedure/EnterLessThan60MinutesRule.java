package com.kcht.parking.charge.procedure;

import java.util.Collections;
import java.util.List;

import com.kcht.parking.charge.charger.Charger;
import com.kcht.parking.charge.timeline.TimeSection;

public class EnterLessThan60MinutesRule implements Rule {
    public EnterLessThan60MinutesRule(Charger charger) {
        this.charger = charger;
    }

    private boolean isIgnoreOthers = false;
    private double charge = 0;
    private Charger charger;

    @Override
    public double charge() {
        return this.charge;
    }

    @Override
    public void process(final List<TimeSection> timeSectionList) {
        int minutes = timeSectionList.stream().map(TimeSection::minutes).reduce((x, y) -> x + y).get();
        if (minutes <= 60) {
            this.isIgnoreOthers = true;
            TimeSection max = timeSectionList.stream().limit(2).max(
                    (o1, o2) -> o1.minutes() >= o2.minutes() ? 1 : -1).get();
            this.charge = charger.charge(max.type(), Collections.singletonList(max));
        }
    }

    @Override
    public boolean ignoreOthers() {
        return this.isIgnoreOthers;
    }
}
