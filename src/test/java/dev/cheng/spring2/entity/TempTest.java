package dev.cheng.spring2.entity;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TempTest {

    @Test
    void test1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String format = formatter.format(new Date());
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        String weekDay = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        int NowWeekDay = Integer.parseInt(weekDay);
        String tomorrowWeekDay = (NowWeekDay + 1) + "";
        String substring = format.substring(4);

        String pwd = substring + weekDay + weekDay + weekDay + tomorrowWeekDay + tomorrowWeekDay + tomorrowWeekDay;
        System.out.println(pwd);

    }
}
