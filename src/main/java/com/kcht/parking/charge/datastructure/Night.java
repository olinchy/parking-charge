package com.kcht.parking.charge.datastructure;

import com.kcht.parking.charge.timeline.TimeSectionType;

/**
 * Created by olinchy on 04/10/2017.
 */
public class Night extends Day {
    public ParkingLotDecorator decorator(final String carType) {
        return parkingLot -> {
            parkingLot.addCharger(CarTypes.valueOf(carType), TimeSectionType.Night, period, price);
            return null;
        };
    }
}
