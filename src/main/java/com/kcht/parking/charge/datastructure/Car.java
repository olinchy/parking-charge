package com.kcht.parking.charge.datastructure;

import java.util.Date;

@SuppressWarnings("ALL")
public class Car {
    public Car(final CarTypes type, final Date enter, final Date exit) {
        this.type = type;
        this.enter = enter;
        this.exit = exit;
    }

    private final Date enter;
    private final Date exit;
    private CarTypes type;

    public Date getEnter() {
        return enter;
    }

    public Date getExit() {
        return exit;
    }

    public CarTypes getType() {
        return type;
    }
}
