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
public class TestChargeTimeShift_2000_2300 {
    public TestChargeTimeShift_2000_2300(final Car car, final double expected) {
        //  this.parkingLot = parkingLot;
        this.car = car;
        this.expected = expected;
    }

    private static ParkingLot parkingLot;
    private Car car;
    private double expected;

    @BeforeClass
    public static void setup() {
        parkingLot = getInstance("charge_detail_another_shift_2000_2300.xml").decideParkingLot(Road);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{
                        // less than 15 minutes
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")), 0},
                new Object[]{
                        // night shift
                        new Car(CarTypes.sedan, date("2017-02-11 20:45:00"), date("2017-02-11 22:48:00")), 0},
                new Object[]{
                        // over night
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-11 07:48:00")), 135},
                new Object[]{
                        // day shift
                        new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 09:48:00")), 27}
        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(parkingLot.charge(car), is(expected));
    }
}
