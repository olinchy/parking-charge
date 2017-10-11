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
public class TestChargePavementOldRule {
    public TestChargePavementOldRule(final Car car, final double expected) {
        this.car = car;
        this.expected = expected;
    }

    private Car car;
    private double expected;

    @BeforeClass
    public static void setup() {
        Api.set(new Config("07:00-22:00", "22:00-07:00", "6+4", "hourly", "6", "once", 15));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
    	
//        {"2017-05-14 15:03:28", "2017-05-15 19:03:28", 88.0F}, // day in another day out
//        {"2017-05-14 15:00:00", "2017-05-15 19:00:00", 84.0F}, // day in another day out
//        {"2017-05-14 15:03:28", "2017-05-14 19:03:28",  18.0F}, // day in same day out
//        {"2017-05-14 15:03:28", "2017-05-14 19:04:28",  22.0F}, // day in same day out
//        {"2017-05-14 15:03:28", "2017-05-16 19:04:28",  154.0F}, // day in two days out
//        
//        {"2017-05-14 15:03:28", "2017-05-14 22:20:28",  36.0F}, // day in same night out
//        {"2017-05-14 15:00:00", "2017-05-14 22:20:28",  36.0F}, // day in same night out
//        {"2017-05-14 15:03:28", "2017-05-16 22:20:28",  168.0F}, // day in another night out
//        
//        {"2017-05-14 22:20:28", "2017-05-15 8:20:28",  16.0F}, // night in next day out
//        {"2017-05-14 22:20:28", "2017-05-15 8:00:00",  12.0F}, // night in next day out
//        {"2017-05-14 22:20:28", "2017-05-16 8:20:28",  82.0F}, // night in another day out
//        
//        {"2017-05-14 22:20:28", "2017-05-15 6:20:28",  6.0F}, // night in same night out
//        {"2017-05-14 22:20:28", "2017-05-16 6:20:28",  74.0F}, // night in another night out
//       {"2017-05-14 22:20:28", "2017-05-17 6:20:30",  140.0F},// night in another night out
//        
//        {"2017-05-14 22:00:00", "2017-05-15 06:59:00",  6.0F},
//        {"2017-05-14 22:00:00", "2017-05-14 22:05:00",  0.0F},
//        {"2017-05-14 21:58:00", "2017-05-14 22:05:00",  0.0F},
//        {"2017-05-14 21:50:00", "2017-05-14 22:40:00",  6.0F},
//        {"2017-05-14 22:00:00", "2017-05-15 07:00:00",  6.0F}, 
//        {"2017-05-14 22:00:00", "2017-05-15 07:01:00",  12.0F}, 
//        {"2017-05-14 07:00:00", "2017-05-14 22:00:00",  62.0F},
        return Arrays.asList(
                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 15:03:28"), date("2017-05-15 19:03:28")), 88},
                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 15:00:00"), date("2017-05-15 19:00:00")), 84},
                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 15:03:28"), date("2017-05-14 19:03:28")), 18},
                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 15:03:28"), date("2017-05-16 19:04:28")), 154}
                //day in,another day out
//                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 22:20:28"), date("2017-05-14 22:20:28")), 36},
//                new Object[]{new Car(CarTypes.sedan, date("2017-05-14 15:00:00"), date("2017-05-14 22:20:28")), 36},
//                //day in,same day out
//                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 06:15:00")), 0},
//                //night in,same night out
//                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 23:15:00")), 38},
//                //night in,another night out
//                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")), 110},
//                //day in,after two days out
//                // 大于12小时过夜
//                new Object[]{new Car(CarTypes.sedan, date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00")), 78}

        );
    }

    @Test
    public void test_charger() throws Exception {
        Assert.assertThat(Api.charge(car), is(expected));
    }
}
