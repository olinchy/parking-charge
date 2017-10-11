package com.kcht.parking.charge.datastructure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.kcht.parking.charge.ParkingLot;

public class CarType implements ParkingLotDecorator {
    public CarType(final String name, final Day day, final Night night) {
        this.name = name;
        this.day = day;
        this.night = night;
    }

    @XmlAttribute
    private String name;
    @XmlElement
    private Day day;
    @XmlElement
    private Night night;

    public void setDay(final Day day) {
        this.day = day;
    }

    public void setNight(final Night night) {
        this.night = night;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public ParkingLot decor(final ParkingLot parkingLot) {
        day.decorator(name).decor(parkingLot);
        night.decorator(name).decor(parkingLot);
        return parkingLot;
    }
}
