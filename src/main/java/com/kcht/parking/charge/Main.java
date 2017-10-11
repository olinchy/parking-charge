/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.kcht.parking.charge;

import java.util.Arrays;

import com.kcht.parking.charge.datastructure.CarType;
import com.kcht.parking.charge.datastructure.CarTypes;
import com.kcht.parking.charge.datastructure.Day;
import com.kcht.parking.charge.datastructure.Level;
import com.kcht.parking.charge.datastructure.Levels;
import com.kcht.parking.charge.datastructure.Night;
import com.kcht.parking.charge.datastructure.ParkingLotDefinition;
import com.kcht.parking.charge.datastructure.Place;

public class Main {
    public static void main(String[] args) {
        ParkingLotDefinition parkingLotDefinition = ParkingLotDefinition.getEmptyInstance();
        parkingLotDefinition.setDay("07:00-22:00");
        parkingLotDefinition.setNight("07:00-22:00");

        parkingLotDefinition.setPlace(
                Arrays.asList(
                        new Place("Pavement", "15",
                                  Arrays.asList(new Level(
                                          Arrays.asList(new CarType(
                                                  CarTypes.sedan.name(),
                                                  new Day("3+4", "hourly"),
                                                  new Night("0", "once"))),
                                          Levels.level_central.name())))));

        ParkingLotDefinition.setInstance(parkingLotDefinition);
    }
}
