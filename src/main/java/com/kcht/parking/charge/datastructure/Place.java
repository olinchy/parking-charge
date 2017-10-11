package com.kcht.parking.charge.datastructure;

import java.util.List;
import java.util.Optional;

import com.kcht.parking.charge.procedure.ExemptRule;

import static com.kcht.parking.charge.StringTool.getNumericFromString;

public class Place {
    public Place() {
    }

    private Place(final String name) {
        this.name = name;
    }

    public Place(final String name, final String exempt, final List<Level> level) {
        this.name = name;
        this.exempt = exempt;
        this.level = level;
    }

    private String name;

    private String exempt;
    private List<Level> level;

    public static Place defaultPlace(final String name) {
        return new Place(name);
    }

    public void setExempt(final String exempt) {
        this.exempt = exempt;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setLevel(final List<Level> level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Place place = (Place) o;

        return name.equals(place.name);
    }

    public ParkingLotDecorator decorator(final Levels level) {
        return parkingLot -> {
            if (exempt != null && !exempt.equals("") && exempt.matches("[\\d]+")) {
                parkingLot.addExempt(new ExemptRule(getNumericFromString(exempt).get(0)));
            }
            Optional<Level> levelOptional = Place.this.level.stream().filter(
                    level1 -> level1.equals(Level.defaultLevel(level.name()))).findFirst();
            Level target = levelOptional.orElseGet(() -> Place.this.level.get(0));
            return target.decor(parkingLot);
        };
    }
}
