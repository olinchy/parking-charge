package com.kcht.parking.charge.datastructure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

import com.kcht.parking.charge.ParkingLot;

/**
 * Created by olinchy on 04/10/2017.
 */
public class Level implements ParkingLotDecorator {
    public Level() {
    }

    private Level(final String desc) {
        this.desc = desc;
    }

    @XmlElement
    private List<CarType> carType;
    @XmlAttribute
    private String desc;

    public static Level defaultLevel(final String desc) {
        return new Level(desc);
    }

    public void setCarType(final List<CarType> carType) {
        this.carType = carType;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    @Override
    public int hashCode() {
        return desc != null ? desc.hashCode() : 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Level level = (Level) o;

        return desc != null ? desc.equals(level.desc) : level.desc == null;
    }

    @Override
    public ParkingLot decor(final ParkingLot parkingLot) {
        for (CarType type : carType) {
            type.decor(parkingLot);
        }
        return parkingLot;
    }
}
