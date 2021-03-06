package com.kcht.parking.charge.datastructure;

import javax.xml.bind.annotation.XmlAttribute;

import com.kcht.parking.charge.ParkingLot;
import com.kcht.parking.charge.timeline.TimeSectionType;

public class Day {
    public Day(final String price, final String period) {
        this.price = price;
        this.period = period;
    }

    @XmlAttribute
    protected String price;
    @XmlAttribute
    protected String period;

    public ParkingLotDecorator decorator(final String carType) {
        return new ParkingLotDecorator() {
            @Override
            public ParkingLot decor(final ParkingLot parkingLot) {
                parkingLot.addCharger(CarTypes.valueOf(carType), TimeSectionType.Day, period, price);
                return null;
            }
        };
    }
}
