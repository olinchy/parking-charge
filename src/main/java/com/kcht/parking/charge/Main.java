/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.kcht.parking.charge;

import com.kcht.parking.charge.Api.Config;

import static com.kcht.parking.charge.datastructure.Car.car;
import static com.kcht.parking.charge.timeline.DateTool.date;

public class Main {
    public static void main(String[] args) {
        Api.set(new Config("07:00-22:00", "22:00-07:00", "3+4", "maxHour[6, 12, 15]", "0", "once", 15));

        double cost = Api.charge(car(date("2017-02-07 07:35:00"), date("2017-02-07 22:35:00")));

        System.out.println(cost);
    }
}
