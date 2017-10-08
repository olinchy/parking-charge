package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.timeline.DatePuncher;
import com.kcht.parking.charge.timeline.Period;
import com.kcht.parking.charge.timeline.TimeSection;
import com.kcht.parking.charge.timerule.TimeRule;
import com.kcht.parking.charge.timerule.TimeRuleFactory;

import static com.kcht.parking.charge.timeline.DateTool.date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 08/10/2017.
 */
@RunWith(Parameterized.class)
public class TestMaxHourTimeRule {
    public TestMaxHourTimeRule(final String period, final Date enter, final Date exit, final Integer expected) {
        this.period = period;
        this.enter = enter;
        this.exit = exit;
        this.expected = expected;
    }

    private String period;
    private Date enter;
    private Date exit;
    private Integer expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{"maxHour[6,12,15]", date("2017-02-10 07:55:20"), date("2017-02-10 08:07:20"), 1},
                new Object[]{"maxHour[6,12,15]", date("2017-02-10 07:04:20"), date("2017-02-10 15:36:20"), 24},
                new Object[]{"maxHour[6,12]", date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00"), 39},
                new Object[]{"maxHour[6,12, 15]", date("2017-02-10 07:32:00"), date("2017-02-12 21:15:00"), 151},
                new Object[]{"maxHour[6,12, 15]", date("2017-02-09 21:32:00"), date("2017-02-11 22:28:00"), 124},
                new Object[]{"maxHour[6,12]", date("2017-02-10 07:32:00"), date("2017-02-10 14:15:00"), 6},
                new Object[]{"maxHour[6,12]", date("2017-02-10 07:32:00"), date("2017-02-10 21:15:00"), 8},
                new Object[]{"maxHour[6,12,15]", date("2017-02-10 07:55:20"), date("2017-02-10 20:07:20"), 25}
        );
    }

    @Test
    public void name() throws Exception {
        TimeRule rule = TimeRuleFactory.createBy(period);
        final Period dayShift = new Period("07:00-22:00");
        final Period nightShift = new Period("22:00-07:00");
        assertThat(
                rule.count(TimeSection.createBy(new DatePuncher(dayShift, nightShift).start(enter, exit), dayShift,
                                                nightShift)), is(expected));
    }
}
