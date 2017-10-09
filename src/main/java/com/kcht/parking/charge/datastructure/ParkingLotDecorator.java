package com.kcht.parking.charge.datastructure;

import com.kcht.parking.charge.ParkingLot;

public interface ParkingLotDecorator {
    ParkingLot decor(ParkingLot parkingLot);
}
