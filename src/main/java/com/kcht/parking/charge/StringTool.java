package com.kcht.parking.charge;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olinchy on 04/10/2017.
 */
public class StringTool {
    public static List<Integer> getNumericFromString(final String string) {
        Pattern pattern = Pattern.compile("([0-9]+)");
        Matcher matcher = pattern.matcher(string);
        final List<Integer> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(Integer.parseInt(matcher.group(1)));
        }
        return list;
    }

}
