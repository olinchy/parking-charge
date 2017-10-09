package com.kcht.parking.charge.timeline;

import java.util.Date;

public class PeriodCombiner {
    public PeriodCombiner(final Period... periods) {
        this.periods = periods;
    }

    private Period[] periods;

    public Date nextSpot(final Date next) {
        for (Period period : periods) {
            if (period.has(next)) {
                return period.nextSpot(next);
            }
        }
        throw new RuntimeException("time " + next + "  is not in shifts");
    }
}
