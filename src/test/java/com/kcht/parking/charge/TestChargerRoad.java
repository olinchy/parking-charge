package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.Api.Config;
import com.kcht.parking.charge.datastructure.CarRecord;
import com.kcht.parking.charge.datastructure.CarTypes;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class TestChargerRoad {
    public TestChargerRoad(final CarRecord car, final double expected) {
        //  this.parkingLot = parkingLot;
        this.car = car;
        this.expected = expected;
    }

    private CarRecord car;
    private double expected;

    @BeforeClass
    public static void setup() {
        Api.set(new Config("07:00-22:00", "22:00-07:00", "3+3", "maxHour[6, 12,15]", "0", "once", 15));
     //   Api.set(new Config("07:00-22:00", "22:00-07:00", "3", "maxHour[6, 12,15]", "0", "once", 15));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{
                        // less than 15 minutes
                        new CarRecord(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")), 0},

                new Object[]{
                        // less than 60 minutes and cross with equal time, charge with day shift
                        new CarRecord(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-10 22:28:00")), 6},
                new Object[]{
                        // not cross shift, on road
                        new CarRecord(CarTypes.sedan, date("2017-02-10 08:32:00"), date("2017-02-10 10:15:00")), 21},
                new Object[]{
                        // cross shift, on road
                        new CarRecord(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-11 08:15:00")), 21},

                new Object[]{
                        // van on pavement, more than 6 less than 12
                        new CarRecord(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00")), 72},

                new Object[]{
                        // van on pavement, more than 12
                        new CarRecord(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")), 93},
                new Object[]{
                        // van on pavement, more than 12 and over night
                        new CarRecord(CarTypes.sedan, date("2017-02-9 23:32:00"), date("2017-02-12 05:15:00")), 216},
                new Object[]{
                        // sedan on Road, more than 12 and over night
                        new CarRecord(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")), 309},

                new Object[]{
                        // sedan on Road, more than 12 and over night
                        new CarRecord(CarTypes.sedan, date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00")), 222}

        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(Api.charge(car), is(expected));
    }
}
