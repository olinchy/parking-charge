package com.kcht.parking.charge;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.kcht.parking.charge.timeline.DatePuncher;
import com.kcht.parking.charge.timeline.Period;
import com.kcht.parking.charge.timeline.TimeSection;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 04/10/2017.
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
}
