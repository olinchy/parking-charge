package com.kcht.parking.charge;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.kcht.parking.charge.timeline.DatePuncher;
import com.kcht.parking.charge.timeline.Period;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timerule.TimeRule;
import com.kcht.parking.charge.timerule.TimeRuleFactory;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by shz on 04/10/2017.
 */
public class TestTimeSection {
    @Test
    public void test() throws Exception {
        Date startDate = date("2017-02-11 21:45:00");
        Date endDate = date("2017-02-11 22:30:00");

        Period day;
        Period night;
        DatePuncher puncher = new DatePuncher(day = new Period("07:00-22:00"), night = new Period("22:00-07:00"));
        List<Date> listDate = puncher.start(startDate, endDate);

        List<TimeSection> list = TimeSection.createBy(listDate, day, night);
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_shift() throws Exception {
        Date startDate = date("2017-02-11 01:45:00");
        Date endDate = date("2017-02-11 04:30:00");

        Period day;
        Period night;
        DatePuncher puncher = new DatePuncher(day = new Period("07:00-00:00"), night = new Period("00:00-07:00"));
        List<Date> listDate = puncher.start(startDate, endDate);

        List<TimeSection> list = TimeSection.createBy(listDate, day, night);
        assertThat(list.size(), is(1));
    }

    @Test
    public void test_shift_1() throws Exception {
        Date startDate = date("2017-02-10 07:45:00");
        Date endDate = date("2017-02-11 07:48:00");

        Period day;
        Period night;
        DatePuncher puncher = new DatePuncher(day = new Period("07:00-00:00"), night = new Period("00:00-07:00"));
        List<Date> listDate = puncher.start(startDate, endDate);

        List<TimeSection> list = TimeSection.createBy(listDate, day, night);
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_overnight() throws Exception {
        TimeRule rule = TimeRuleFactory.createBy("maxHour[6,12,15]");
        final Period dayShift = new Period("07:00-00:00");
        final Period nightShift = new Period("00:00-07:00");
        assertThat(
                rule.count(TimeSection.createBy(new DatePuncher(dayShift, nightShift).start(
                        date("2017-02-10 07:45:00"), date("2017-02-11 07:48:00")), dayShift,
                                                nightShift)), is(69));
    }
}
