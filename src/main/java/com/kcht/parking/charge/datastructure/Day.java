package com.kcht.parking.charge.datastructure;

import javax.xml.bind.annotation.XmlAttribute;

import com.kcht.parking.charge.timeline.TimeSectionType;

/**
 * Created by olinchy on 04/10/2017.
 */
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
