package com.kcht.parking.charge.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kcht.parking.charge.charger.Charger;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timeline.TimeSectionType;

public class NormalRule implements Rule {
    public NormalRule(final Charger charger) {
        this.charger = charger;
    }

    private Charger charger;
    private double charge;

    @Override
    public double charge() {
        return charge;
    }

    @Override
    public void process(final List<TimeSection> timeSectionList) {
        HashMap<TimeSectionType, List<TimeSection>> map = new HashMap<>();
        for (TimeSection timeSection : timeSectionList) {
            List<TimeSection> list = map.computeIfAbsent(timeSection.type(), k -> new ArrayList<>());
            list.add(timeSection);
        }
        map.entrySet().stream().forEach(entry -> charge += charger.charge(entry.getKey(), entry.getValue()));
    }

    @Override
    public boolean ignoreOthers() {
        return false;
    }
}
