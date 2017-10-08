package com.kcht.parking.charge.timerule;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kcht.parking.charge.StringTool.getNumericFromString;

/**
 * Created by olinchy on 04/10/2017.
 */
public class TimeRuleFactory {
    public static TimeRule createBy(final String period) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(period);
        if (matcher.find()) {
            return TimeRules.valueOf(matcher.group(1)).createBy(period);
        }

        return null;
    }

    enum TimeRules implements TimeRuleCreator {
        hourly {
            @Override
            public TimeRule createBy(final String period) {
                return new MinutesTimeRule(60);
            }
        }, maxHour {
            @Override
            public TimeRule createBy(final String period) {
                List<Integer> list = getNumericFromString(period);
                return new MaxHourTimeRule(list.get(0), list.get(1));
            }
        }, minutes {
            @Override
            public TimeRule createBy(final String period) {
                return new MinutesTimeRule(getNumericFromString(period).get(0));
            }
        }, once {
            @Override
            public TimeRule createBy(final String period) {
                return new OnceTimeRule();
            }
        }
    }

    interface TimeRuleCreator {
        TimeRule createBy(String period);
    }
}
