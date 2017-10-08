package com.kcht.parking.charge.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by olinchy on 04/10/2017.
 */
public class TimeSection {
    public TimeSection(final Date start, final Date end, final TimeSectionType timeSectionType) {
        this.start = start;
        this.end = end;
        this.type = timeSectionType;
    }

    private final Date start;
    private final Date end;
    private final TimeSectionType type;

    public static List<TimeSection> createBy(
            final List<Date> list, final Period dayShift, final Period nightShift) {
        ArrayList<TimeSection> timeSections = new ArrayList<>();
        final LinkedList<Date> linkedListDate = new LinkedList<>(list);
        while (linkedListDate.size() > 1) {
            Date start = linkedListDate.pop();
            Date end = linkedListDate.peekFirst();
            TimeSectionType timeSectionType;
            if (dayShift.has(start) && !nightShift.has(start)) {
                timeSectionType = TimeSectionType.Day;
            } else {
                timeSectionType = TimeSectionType.Night;
            }
            timeSections.add(new TimeSection(start, end, timeSectionType));
        }
        return timeSections;
    }

    public int minutes() {
        return (int) ((end.getTime() - start.getTime()) / 1000 / 60);
    }

    public TimeSectionType type() {
        return type;
    }
}
