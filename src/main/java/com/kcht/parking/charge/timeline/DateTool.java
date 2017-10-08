package com.kcht.parking.charge.timeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olinchy on 04/10/2017.
 */
public class DateTool {
    public static Date date(final String strDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
