
package com.kcht.parking.charge;

import com.kcht.parking.charge.Api.Config;

import static com.kcht.parking.charge.datastructure.Car.car;
import static com.kcht.parking.charge.timeline.DateTool.date;

public class Main {
    public static void main(String[] args) {
    	//dayMode 可选项:"maxHour[6, 12, 15]", maxHour[最大时间，阶段时间，计时单位] maxHour[6,12,60]表示12小时以内计时上限为6小时，12小时以外累计，计时单位为15分钟-->
    	//dayMode 可选项:"hourly",按小时计费,无计时上限限制
        Api.set(new Config("07:00-22:00", "22:00-07:00", "3+4", "maxHour[6, 12, 15]", "0", "once", 15));

        double cost = Api.charge(car(date("2017-02-07 07:35:00"), date("2017-02-07 22:35:00")));

        System.out.println(cost);
    }
}
