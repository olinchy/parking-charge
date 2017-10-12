package com.kcht.parking.charge.charger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kcht.parking.charge.procedure.EnterLessThan60MinutesRule;
import com.kcht.parking.charge.procedure.NormalRule;
import com.kcht.parking.charge.procedure.Rule;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timeline.TimeSectionType;
import com.kcht.parking.charge.tools.LambdaConverter;
import com.kcht.parking.charge.tools.LambdaReducer;
import com.kcht.parking.charge.tools.To;

public class Charger {
    public Charger(final HashMap<TimeSectionType, ChargerRule> chargerRule) {
        rules = new ArrayList<Rule>();
        rules.add(new EnterLessThan60MinutesRule(this));
        rules.add(new NormalRule(this));

        this.chargerRule = chargerRule;
    }

    private List<Rule> rules;
    private HashMap<TimeSectionType, ChargerRule> chargerRule = new HashMap<>();

    public double placeCharge(final List<TimeSection> timeSectionList) {
        for (final Rule next : rules) {
            next.process(timeSectionList);
            if (next.ignoreOthers()) {
                return next.charge();
            }
        }

        return To.reduce(To.map(rules, new LambdaConverter<Double, Rule>() {
            @Override
            public Double to(final Rule content) {
                return content.charge();
            }
        }), new LambdaReducer<Double>() {
            @Override
            public Double reduce(final Double x, final Double y) {
                return x + y;
            }
        });
    }

    public double charge(TimeSectionType type, final List<TimeSection> timeSections) {
        return chargerRule.get(type).charge(timeSections);
    }

    public Charger addExempt(final Rule exemptRule) {
        this.rules.add(0, exemptRule);
        return this;
    }
}
