package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kcht.parking.charge.timerule.MaxHourTimeRule;
import com.kcht.parking.charge.timerule.MinutesTimeRule;
import com.kcht.parking.charge.timerule.OnceTimeRule;
import com.kcht.parking.charge.timerule.TimeRule;
import com.kcht.parking.charge.timerule.TimeRuleFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by shz on 04/10/2017.
 */
@RunWith(Parameterized.class)
public class TestCreateTimeRule {
    public TestCreateTimeRule(final String period, final Class<?> expected) {
        this.expected = expected;
        this.period = period;
    }

    private Class<?> expected;
    private String period;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{"minutes[60]", MinutesTimeRule.class},
                new Object[]{"once", OnceTimeRule.class},
                new Object[]{"hourly", MinutesTimeRule.class},
                new Object[]{"maxHour[6,12,15]", MaxHourTimeRule.class},
                new Object[]{"maxHour[6,12]", MaxHourTimeRule.class}
        );
    }

    @Test
    public void name() throws Exception {
        TimeRule rule = TimeRuleFactory.createBy(period);
        assertThat(rule.getClass().getSimpleName(), is(expected.getSimpleName()));
    }
}
