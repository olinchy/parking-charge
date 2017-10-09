package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
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
import static com.kcht.parking.charge.datastructure.Places.Road;
import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;


@RunWith(Parameterized.class)
public class TestChargeTimeShift20To23 {
	
	
	
    public TestChargeTimeShift20To23( final Car car, final double expected) {
      //  this.parkingLot = parkingLot;
        this.car = car;
        this.expected = expected;
    }
    private static ParkingLot parkingLot;
    private Car car;
    private double expected;
    
    
    @BeforeClass
    public static void setup(){
    	parkingLot = getInstance().decideParkingLot(Pavement);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:45:00"), date("2017-02-10 07:48:00")),0},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-10 23:59:00")), 6},
                new Object[]{ new Car(CarTypes.sedan, date("2017-02-10 08:32:00"), date("2017-02-10 10:15:00")), 10},
                new Object[]{ new Car(CarTypes.sedan, date("2017-02-10 21:32:00"), date("2017-02-11 08:15:00")), 26},//day in,another day out 
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00")), 26},
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00")),34},//day in,same day out
                
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 06:15:00")),26},//night in,same night out 
               
                
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 23:32:00"), date("2017-02-11 23:15:00")),54},//night in,another night out 
               
                
                new Object[]{new Car(CarTypes.sedan, date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00")),126},//day in,after two days out
                	// 大于12小时过夜
                new Object[]{ new Car(CarTypes.sedan, date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00")),98}

        );
    }

    @Test
    public void test_charger() throws Exception {
            Assert.assertThat(parkingLot.charge(car), is(expected));
    }
    
    
    
}
