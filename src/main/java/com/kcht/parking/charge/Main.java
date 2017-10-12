
package com.kcht.parking.charge;

import com.kcht.parking.charge.Api.Config;

import static com.kcht.parking.charge.datastructure.Car.car;
import static com.kcht.parking.charge.timeline.DateTool.date;

public class Main {
    public static void main(String[] args) {
    	//dayMode ��ѡ��:"maxHour[6, 12, 15]", maxHour[���ʱ�䣬�׶�ʱ�䣬��ʱ��λ] maxHour[6,12,60]��ʾ12Сʱ���ڼ�ʱ����Ϊ6Сʱ��12Сʱ�����ۼƣ���ʱ��λΪ15����-->
    	//dayMode ��ѡ��:"hourly",��Сʱ�Ʒ�,�޼�ʱ��������
        Api.set(new Config("07:00-22:00", "22:00-07:00", "3+4", "maxHour[6, 12, 15]", "0", "once", 15));

        double cost = Api.charge(car(date("2017-02-07 07:35:00"), date("2017-02-07 22:35:00")));

        System.out.println(cost);
    }
}
