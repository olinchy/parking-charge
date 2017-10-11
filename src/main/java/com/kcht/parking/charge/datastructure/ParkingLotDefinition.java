package com.kcht.parking.charge.datastructure;

import java.util.List;


import com.kcht.parking.charge.ParkingLot;
import com.kcht.parking.charge.timeline.Period;

public class ParkingLotDefinition {
    private ParkingLotDefinition() {

    }

    private static ParkingLotDefinition self;
    private List<Place> place;
    private String day;
    private String night;


    public static void setInstance(ParkingLotDefinition parkingLotDefinition) {
        self = parkingLotDefinition;
    }

    public static ParkingLotDefinition getEmptyInstance() {
        return new ParkingLotDefinition();
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public void setPlace(final List<Place> place) {
        this.place = place;
    }

    public void setNight(final String night) {
        this.night = night;
    }

    public ParkingLot decideParkingLot() {
        return this.decideParkingLot(Places.Pavement, Levels.level_central);
    }

    public ParkingLot decideParkingLot(final Places placeType, final Levels level) {
        ParkingLot parkingLot = new ParkingLot(new Period(day), new Period(night));

        Place target = this.place.stream().filter(
                place1 -> place1.equals(Place.defaultPlace(placeType.name()))).findFirst().get();

        return target.decorator(level).decor(parkingLot);
    }

    public ParkingLot decideParkingLot(final Places placeType) {
        return this.decideParkingLot(placeType, Levels.level_central);
    }
}
