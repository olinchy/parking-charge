
package com.kcht.parking.charge;

class ParkingLotInstance {
    private static ParkingLot parkingLot;

    public static ParkingLot getInstance() {
        return parkingLot;
    }

    public static void setParkingLot(final ParkingLot parkingLot) {
        ParkingLotInstance.parkingLot = parkingLot;
    }
}
