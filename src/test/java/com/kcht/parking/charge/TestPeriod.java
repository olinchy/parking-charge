package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.timeline.Period;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by shz on 04/10/2017.
 */
@RunWith(Parameterized.class)
public class TestPeriod {
    public TestPeriod(final Period period, final Date date, boolean expected) {
        this.period = period;
        this.date = date;
        this.expected = expected;
    }

    private Period period;
    private Date date;
    private boolean expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new Period("14:00-17:00"), date("2017-02-10 14:00:00"), true},
                new Object[]{new Period("14:00-17:00"), date("2017-02-10 15:57:12"), true},
                new Object[]{new Period("14:00-17:00"), date("2017-02-10 17:00:00"), false},
                new Object[]{new Period("07:00-22:00"), date("2017-02-10 22:00:00"), false},
                new Object[]{new Period("00:00-07:00"), date("2017-02-10 04:00:00"), true},
                new Object[]{new Period("00:00-07:00"), date("2017-02-10 08:00:00"), false},
                new Object[]{new Period("07:00-00:00"), date("2017-02-10 08:00:00"), true},
                new Object[]{new Period("07:00-00:00"), date("2017-02-10 07:00:00"), true},
                new Object[]{new Period("07:00-00:00"), date("2017-02-10 06:59:00"), false},
                new Object[]{new Period("07:00-00:00"), date("2017-02-10 06:59:00"), false},
                new Object[]{new Period("23:00-20:00"), date("2017-02-10 07:45:00"), true},
                new Object[]{new Period("20:00-23:00"), date("2017-02-10 07:45:00"), false},
                new Object[]{new Period("22:00-07:00"), date("2017-02-10 22:00:00"), true}
        );
    }

    @Test
    public void testHas() throws Exception {
        assertThat(period.has(date), is(expected));
    }
}
