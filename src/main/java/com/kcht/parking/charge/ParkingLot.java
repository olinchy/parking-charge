package com.kcht.parking.charge;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kcht.parking.charge.charger.Charger;
import com.kcht.parking.charge.charger.ChargerRule;
import com.kcht.parking.charge.charger.HeadTailCharger;
import com.kcht.parking.charge.charger.StepCharger;
import com.kcht.parking.charge.datastructure.CarRecord;
import com.kcht.parking.charge.datastructure.CarTypes;
import com.kcht.parking.charge.procedure.ExemptRule;
import com.kcht.parking.charge.timeline.DatePuncher;
import com.kcht.parking.charge.timeline.Period;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timeline.TimeSectionType;
import com.kcht.parking.charge.timerule.TimeRule;
import com.kcht.parking.charge.timerule.TimeRuleFactory;

public class ParkingLot {
    public ParkingLot(final Period dayShift, final Period nightShift) {
        this.dayShift = dayShift;
        this.nightShift = nightShift;
        if (!dayShift.connectWith(nightShift)) {
            throw new IllegalArgumentException(
                    "day shift and night shift should cover 24 hours day shift :" + dayShift.toString()
                            + " night shift: " + nightShift.toString());
        }
    }

    private Period dayShift;
    private Period nightShift;
    private HashMap<CarTypes, HashMap<TimeSectionType, ChargerRule>> rules = new HashMap<>();
    private ExemptRule exemptRule;

    public double charge(final CarRecord car) {

        Charger charger = decideCharger(car.getType());

        List<TimeSection> timeSectionList = toTimeSections(car.getEnter(), car.getExit());

        return charger.placeCharge(timeSectionList);
    }

    private Charger decideCharger(final CarTypes type) {
        if (exemptRule != null) {
            return new Charger(rules.get(type)).addExempt(exemptRule);
        } else {
            return new Charger(rules.get(type));
        }
    }

    private List<TimeSection> toTimeSections(final Date enter, final Date exit) {
        DatePuncher datePuncher = new DatePuncher(this.dayShift, this.nightShift);
        return TimeSection.createBy(datePuncher.start(enter, exit), dayShift, nightShift);
    }

    public void addCharger(
            final CarTypes carTypes, final TimeSectionType type, final String period, final String price) {
        HashMap<TimeSectionType, ChargerRule> map = rules.get(carTypes);
        if (map == null) {
            map = new HashMap<>();
        }

        map.put(type, getChargeRule(period, price));
        rules.put(carTypes, map);
    }

    private ChargerRule getChargeRule(final String period, final String price) {
        TimeRule timeRule = TimeRuleFactory.createBy(period);
        if (price.matches("[\\d]+\\+[\\d]+")) {
            String[] split = price.split("\\+");
            return new HeadTailCharger(Double.parseDouble(split[0]), Double.parseDouble(split[1]), timeRule);
        } else {
            return new StepCharger(Double.parseDouble(price), timeRule);
        }
    }

    public void addExempt(final ExemptRule exemptRule) {
        this.exemptRule = exemptRule;
    }
}
