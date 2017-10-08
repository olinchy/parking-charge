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
import com.kcht.parking.charge.datastructure.ParkingLotDefinition;

import static com.kcht.parking.charge.datastructure.Levels.level_central;
import static com.kcht.parking.charge.datastructure.Levels.level_primary;
import static com.kcht.parking.charge.datastructure.Levels.level_secondary;
import static com.kcht.parking.charge.datastructure.ParkingLotDefinition.getInstance;
import static com.kcht.parking.charge.datastructure.Places.Pavement;
import static com.kcht.parking.charge.datastructure.Places.PublicFacility;
import static com.kcht.parking.charge.datastructure.Places.Road;
import static com.kcht.parking.charge.datastructure.Places.TransportJunction;
import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;

/**
 * Created by olinchy on 04/10/2017.
 */
@RunWith(Parameterized.class)
public class TestCharger {
    public TestCharger(final ParkingLot parkingLot, final Car car, final double expected) {
        this.parkingLot = parkingLot;
        this.car = car;
        this.expected = expected;
    }
    private ParkingLot parkingLot;
    private Car car;
    private double expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{
                        // less than 15 minutes
                        getInstance().decideParkingLot(PublicFacility, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")),
                        0},
                new Object[]{
                        // less than 15 minutes
                        getInstance().decideParkingLot(Pavement, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")),
                        6},
                new Object[]{
                        // less than 60 minutes and cross with equal time, charge with day shift
                        getInstance().decideParkingLot(TransportJunction, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-10 22:28:00")),
                        5},
                new Object[]{
                        // not cross shift, on road
                        getInstance().decideParkingLot(Road, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 08:32:00"), date("2017-02-10 10:15:00")),
                        21},
                new Object[]{
                        // cross shift, on road
                        getInstance().decideParkingLot(Road, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-11 08:15:00")),
                        21},
                new Object[]{
                        // van on train station, for more than two days
                        getInstance().decideParkingLot(TransportJunction, level_central),
                        new Car(CarTypes.van, date("2017-02-10 21:32:00"), date("2017-02-12 23:15:00")),
                        245},
                new Object[]{
                        // van on pavement, more than 6 less than 12
                        getInstance().decideParkingLot(Pavement, level_primary),
                        new Car(CarTypes.van, date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00")),
                        32},
                new Object[]{
                        // van on secondary pavement, more than 6 less than 12
                        getInstance().decideParkingLot(Pavement, level_secondary),
                        new Car(CarTypes.van, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")),
                        34},
                new Object[]{
                        // van on pavement, more than 12
                        getInstance().decideParkingLot(Pavement, level_primary),
                        new Car(CarTypes.van, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")),
                        42},
                new Object[]{
                        // van on pavement, more than 12 and over night
                        getInstance().decideParkingLot(Pavement, level_primary),
                        new Car(CarTypes.van, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")),
                        137},
                new Object[]{
                        // less than 60 minutes and cross with longer at night shift, means charge with night shift
                        getInstance().decideParkingLot(TransportJunction, level_central),
                        new Car(CarTypes.sedan, date("2017-02-10 21:45:00"), date("2017-02-10 22:30:00")),
                        6}
        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(parkingLot.charge(car), is(expected));
    }
}
