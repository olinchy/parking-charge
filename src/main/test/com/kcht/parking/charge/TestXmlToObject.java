package com.kcht.parking.charge;

import org.junit.Test;

import com.kcht.parking.charge.datastructure.ParkingLotDefinition;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 04/10/2017.
 */
public class TestXmlToObject {
    @Test
    public void testMarshall() throws Exception {
        ParkingLotDefinition parkingLotDefinition = ParkingLotDefinition.getInstance();
        assertTrue(parkingLotDefinition != null);
    }
}
