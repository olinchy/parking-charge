/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.kcht.parking.charge;

import com.kcht.parking.charge.datastructure.Car;
import com.kcht.parking.charge.datastructure.CarType;
import com.kcht.parking.charge.datastructure.CarTypes;
import com.kcht.parking.charge.datastructure.Day;
import com.kcht.parking.charge.datastructure.Level;
import com.kcht.parking.charge.datastructure.Levels;
import com.kcht.parking.charge.datastructure.Night;
import com.kcht.parking.charge.datastructure.ParkingLotDefinition;
import com.kcht.parking.charge.datastructure.Place;

import static java.util.Collections.singletonList;

public class Api {
    public static void set(Config config) {
        ParkingLotDefinition parkingLotDefinition = ParkingLotDefinition.getEmptyInstance();
        parkingLotDefinition.setDay(config.dayShift);
        parkingLotDefinition.setNight(config.nightShift);

        parkingLotDefinition.setPlace(
                singletonList(
                        new Place("Pavement", String.valueOf(config.exempt),
                                  singletonList(new Level(
                                          singletonList(new CarType(
                                                  CarTypes.sedan.name(),
                                                  new Day(config.dayPrice, config.dayMode),
                                                  new Night(config.nightPrice, config.nightMode))),
                                          Levels.level_central.name())))));

        ParkingLotInstance.setParkingLot(parkingLotDefinition.decideParkingLot());
    }

    public static Double charge(final Car car) {
        return ParkingLotInstance.getInstance().charge(car);
    }

    public static class Config {
        public Config(
                final String dayShift, final String nightShift, final String dayPrice, final String dayMode,
                final String nightPrice,
                final String nightMode, final int exempt) {
            this.dayShift = dayShift;
            this.nightShift = nightShift;
            this.dayPrice = dayPrice;
            this.dayMode = dayMode;
            this.nightPrice = nightPrice;
            this.nightMode = nightMode;
            this.exempt = exempt;
        }

        private String dayShift;
        private String nightShift;
        private String dayPrice;
        private String dayMode;
        private String nightPrice;
        private String nightMode;
        private int exempt;
    }
}
