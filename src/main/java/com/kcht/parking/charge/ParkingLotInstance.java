/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

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
