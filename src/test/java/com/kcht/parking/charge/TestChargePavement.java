package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.Api.Config;
import com.kcht.parking.charge.datastructure.Car;
import com.kcht.parking.charge.datastructure.CarTypes;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class TestChargePavement {
    public TestChargePavement(final Car car, final double expected) {
        this.car = car;
        this.expected = expected;
    }

    private Car car;
    private double expected;

    @BeforeClass
    public static void setup() {
        Api.set(new Config("07:00-22:00", "22:00-07:00", "6+4", "maxHour[6, 12,60]", "0", "once", 15));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")), 0},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-10 22:28:00")), 6},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 08:32:00"), date("2017-02-10 10:15:00")), 10},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-11 08:15:00")), 14},
                //day in,another day out
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00")), 26},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")), 34},
                //day in,same day out
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 06:15:00")), 0},
                //night in,same night out
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 23:15:00")), 38},
                //night in,another night out
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")), 110},
                //day in,after two days out
                // 大于12小时过夜
                new Object[]{new Car(CarTypes.sedan, date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00")), 78}

        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(Api.charge(car), is(expected));
    }
}
