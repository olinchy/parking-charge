package com.kcht.parking.charge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Created by shz on 04/10/2017.
 */
public class TestPattern {
    @Test
    public void test() throws Exception {
        Pattern pattern = Pattern.compile("([0-9]+)");
        Matcher matcher = pattern.matcher("minutes[16, 66]");
        while (matcher.find())
            System.out.println(matcher.group(1));
    }
}
