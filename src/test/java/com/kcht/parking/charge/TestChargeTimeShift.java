package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.datastructure.Car;
import com.kcht.parking.charge.datastructure.CarTypes;

import static com.kcht.parking.charge.datastructure.ParkingLotDefinition.getInstance;
import static com.kcht.parking.charge.datastructure.Places.Road;
import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class TestChargeTimeShift {
    public TestChargeTimeShift(final Car car, final double expected) {
        //  this.parkingLot = parkingLot;
        this.car = car;
        this.expected = expected;
    }

    private static ParkingLot parkingLot;
    private Car car;
    private double expected;

    @BeforeClass
    public static void setup() {
        parkingLot = getInstance().decideParkingLot(Road);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{
                        // less than 15 minutes
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")), 0},

                new Object[]{
                        // less than 60 minutes and cross with equal time, charge with day shift
                        new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-10 22:28:00")), 12},
                new Object[]{
                        // not cross shift, on road
                        new Car(CarTypes.sedan, date("2017-02-10 08:32:00"), date("2017-02-10 10:15:00")), 21},
                new Object[]{
                        // cross shift, on road
                        new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-11 08:15:00")), 45},

                new Object[]{
                        // van on pavement, more than 6 less than 12
                        new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00")), 72},

                new Object[]{
                        // van on pavement, more than 12
                        new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")), 93},
                new Object[]{
                        // van on pavement, more than 12 and over night
                        new Car(CarTypes.sedan, date("2017-02-9 23:32:00"), date("2017-02-12 05:15:00")), 264},
                new Object[]{
                        // sedan on Road, more than 12 and over night
                        new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")), 357},

                new Object[]{
                        // sedan on Road, more than 12 and over night
                        new Car(CarTypes.sedan, date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00")), 276}

        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(parkingLot.charge(car), is(expected));
    }
}
