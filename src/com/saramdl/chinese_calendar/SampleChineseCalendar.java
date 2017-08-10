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
		
		LunarConjunction lunar = new LunarConjunction();
		Calendar cal_lunar;
		cal_lunar = lunar.getPreviousConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("이번달 합삭시(초하루) : " + sdf.format(cal_lunar.getTime()));
		
		cal_lunar = lunar.getNextConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("다음달 합삭시(초하루) : " + sdf.format(cal_lunar.getTime()));

		cal_lunar = lunar.getFullMoon(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("이번달 망시간(보름날) : " + sdf.format(cal_lunar.getTime()));
	}

}
