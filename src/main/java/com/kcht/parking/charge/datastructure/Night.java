package com.kcht.parking.charge.datastructure;

import com.kcht.parking.charge.ParkingLot;
import com.kcht.parking.charge.timeline.TimeSectionType;

public class Night extends Day {
    public Night(final String price, final String period) {
        super(price, period);
    }

    public ParkingLotDecorator decorator(final String carType) {
        return new ParkingLotDecorator() {
            @Override
            public ParkingLot decor(final ParkingLot parkingLot) {
                parkingLot.addCharger(CarTypes.valueOf(carType), TimeSectionType.Night, period, price);
                return null;
            }
        };
    }
}
