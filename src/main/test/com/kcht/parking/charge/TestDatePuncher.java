package com.kcht.parking.charge;

import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;

import com.kcht.parking.charge.timeline.DatePuncher;
import com.kcht.parking.charge.timeline.Period;

import static com.kcht.parking.charge.timeline.DateTool.date;

/**
 * Created by olinchy on 04/10/2017.
 */
public class TestDatePuncher {
    @Test
    public void test() throws Exception {
        Date startDate = date("2017-02-11 21:45:00");
        Date endDate = date("2017-02-11 22:30:00");

        DatePuncher puncher = new DatePuncher(new Period("07:00-22:00"), new Period("22:00-07:00"));
        puncher.start(startDate, endDate);

        final LinkedList<Date> listDate = new LinkedList<>();
        listDate.push(startDate);
        while (!puncher.stopped()) {
            listDate.push(puncher.next());
        }
        System.out.println(listDate);
    }
}
