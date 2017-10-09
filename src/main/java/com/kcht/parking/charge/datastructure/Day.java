package com.kcht.parking.charge.datastructure;

import javax.xml.bind.annotation.XmlAttribute;

import com.kcht.parking.charge.timeline.TimeSectionType;

public class Day {
    @XmlAttribute
    protected String price;
    @XmlAttribute
    protected String period;

    public void setPrice(final String price) {
        this.price = price;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    public ParkingLotDecorator decorator(final String carType) {
        return parkingLot -> {
            parkingLot.addCharger(CarTypes.valueOf(carType), TimeSectionType.Day, period, price);
            return null;
        };
    }
}
