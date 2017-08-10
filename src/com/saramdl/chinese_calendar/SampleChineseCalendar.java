package com.saramdl.chinese_calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SampleChineseCalendar {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		
		Calendar gcal = new GregorianCalendar();
		ChineseCalendar cal = new ChineseCalendar();
		
		cal.GYear 	= gcal.get(Calendar.YEAR);
		cal.GMonth 	= gcal.get(Calendar.MONTH) + 1;
		cal.GDay 	= gcal.get(Calendar.DAY_OF_MONTH);
		cal.GHour 	= gcal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		cal.GMinute = gcal.get(Calendar.MINUTE);
		
		System.out.println(sdf.format(gcal.getTime()));
		cal.convert();
		System.out.println(cal.CYear + "년 " + cal.CMonth + "월 " + cal.CDay + "일 " + cal.CHour + "시");
	}

}
