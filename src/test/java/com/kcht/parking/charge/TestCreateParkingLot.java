package com.kcht.parking.charge;

import org.junit.Test;

import com.kcht.parking.charge.datastructure.Levels;
import com.kcht.parking.charge.datastructure.ParkingLotDefinition;
import com.kcht.parking.charge.datastructure.Places;

import static org.junit.Assert.assertTrue;

/**
 * Created by shz on 04/10/2017.
 */
public class TestCreateParkingLot {
    @Test
    public void test() throws Exception {
        ParkingLot parkingLot = ParkingLotDefinition.getInstance().decideParkingLot(
                Places.Road, Levels.level_central);
        assertTrue(parkingLot != null);
    }
}
