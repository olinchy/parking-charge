package com.kcht.parking.charge.datastructure;

import com.kcht.parking.charge.ParkingLot;

/**
 * Created by olinchy on 04/10/2017.
 */
public interface ParkingLotDecorator {
    ParkingLot decor(ParkingLot parkingLot);
}
