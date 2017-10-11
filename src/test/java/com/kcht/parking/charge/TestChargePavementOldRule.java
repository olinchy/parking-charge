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
    public TestChargePavementOldRule(final String enter, final String exit, final double expected) {
        this.expected = expected;
        this.enter = enter;
        this.exit = exit;
    }

    private double expected;
    private String enter;
    private String exit;

    @BeforeClass
    public static void setup() {
        Api.set(new Config("07:00-22:00", "22:00-07:00", "6+4", "hourly", "6", "once", 15));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(
                new Object[][]{
                        {"2017-05-14 15:03:28", "2017-05-15 19:03:28", 88},//78},
                        {"2017-05-14 15:00:00", "2017-05-15 19:00:00", 84},//84},
                        {"2017-05-14 15:03:28", "2017-05-14 19:03:28", 18},
                        {"2017-02-10 21:32:00", "2017-02-11 08:15:00", 20},//14},
                        {"2017-02-10 07:32:00", "2017-02-10 14:15:00", 30},//day in,another day out
                        {"2017-02-10 07:32:00", "2017-02-10 21:15:00", 58},
                        {"2017-02-10 23:32:00", "2017-02-11 06:15:00", 6},//day in,same day out
                        {"2017-02-10 23:32:00", "2017-02-11 23:15:00", 74},//night in,same night out
                        {"2017-02-10 07:32:00", "2017-02-12 21:15:00", 194},//night in,another night out
                        {"2017-02-09 21:32:00", "2017-02-11 22:28:00", 144},// 大于12小时过夜
                        {"2017-05-14 15:03:28", "2017-05-15 19:03:28", 88}, // day in another day out
                        {"2017-05-14 15:00:00", "2017-05-15 19:00:00", 84.0}, // day in another day out
                        {"2017-05-14 15:03:28", "2017-05-14 19:03:28", 18.0}, // day in same day out
                        {"2017-05-14 15:03:28", "2017-05-14 19:04:28", 22.0}, // day in same day out
                        {"2017-05-14 15:03:28", "2017-05-16 19:04:28", 154}, // day in two days out
                        {"2017-05-14 15:03:28", "2017-05-14 22:20:28", 36.0}, // day in same night out
                        {"2017-05-14 15:00:00", "2017-05-14 22:20:28", 36.0}, // day in same night out
                        {"2017-05-14 15:03:28", "2017-05-16 22:20:28", 168.0}, // day in another night out
                        {"2017-05-14 22:20:28", "2017-05-15 8:20:28", 16.0}, // night in next day out
                        {"2017-05-14 22:20:28", "2017-05-15 8:00:00", 12.0}, // night in next day out
                        {"2017-05-14 22:20:28", "2017-05-16 8:20:28", 82.0}, // night in another day out
                        {"2017-05-14 22:20:28", "2017-05-15 6:20:28", 6.0}, // night in same night out
                        {"2017-05-14 22:20:28", "2017-05-16 6:20:28", 74.0}, // night in another night out
                        {"2017-05-14 22:20:28", "2017-05-17 6:20:30", 140.0},// night in another night out
                        {"2017-05-14 22:00:00", "2017-05-15 06:59:00", 6.0},
                        {"2017-05-14 22:00:00", "2017-05-14 22:05:00", 0.0},
                        {"2017-05-14 21:58:00", "2017-05-14 22:05:00", 0.0},
                        {"2017-05-14 21:50:00", "2017-05-14 22:40:00", 6.0},
                        {"2017-05-14 22:00:00", "2017-05-15 07:00:00", 6.0}, // TODO: 7:00:00是不是该算白天
                        {"2017-05-14 22:00:00", "2017-05-15 07:01:00", 12.0},
                        {"2017-05-14 07:00:00", "2017-05-14 22:00:00", 62.0}
                }
        );
    }

    @Test
    public void test_charger() throws Exception {
        Car car = new Car(CarTypes.sedan, date(enter), date(exit));
        Assert.assertThat(Api.charge(car), is(expected));
    }
}
