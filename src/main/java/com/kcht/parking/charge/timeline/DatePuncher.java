package com.kcht.parking.charge.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatePuncher {
    public DatePuncher(final Period dayShift, final Period nightShift) {
        this.shifts = new PeriodCombiner(dayShift, nightShift);
    }

    private final PeriodCombiner shifts;
    private TimeLine timeLine;

    public List<Date> start(final Date enter, final Date exit) {
        this.timeLine = new TimeLine(enter, shifts, exit);

        ArrayList<Date> listDate = new ArrayList<>();
        listDate.add(enter);
        while (!timeLine.stopped()) {
            listDate.add(timeLine.next());
        }
        return listDate;
    }

    private class TimeLine {
        public TimeLine(final Date enter, final PeriodCombiner shifts, final Date exit) {
            this.start = enter;
            this.end = exit;
            this.next = start;
            this.shifts = shifts;
        }

        private final Date start;
        private final Date end;
        private final PeriodCombiner shifts;
        private Date next;

        public Date next() {
            next = shifts.nextSpot(next);
            if (next.before(end)) {
                return next;
            }
            return (next = end);
        }

        public boolean stopped() {
            return next.equals(end);
        }
    }
}
