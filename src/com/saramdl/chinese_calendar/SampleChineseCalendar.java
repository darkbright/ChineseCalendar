package com.saramdl.chinese_calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.ibm.icu.util.ChineseCalendar;

public class SampleChineseCalendar {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 dd일 E요일 HH시 mm분");
		
		Calendar gcal = new GregorianCalendar();
		SexagenaryCalendar cal = new SexagenaryCalendar();
		
		cal.GYear 	= gcal.get(Calendar.YEAR);
		cal.GMonth 	= gcal.get(Calendar.MONTH) + 1;
		cal.GDay 	= gcal.get(Calendar.DAY_OF_MONTH);
		cal.GHour 	= gcal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		cal.GMinute = gcal.get(Calendar.MINUTE);
		
		System.out.println("양력 " + sdf.format(gcal.getTime()));
		
        //양력->음력 변환
        ChineseCalendar lcal = new ChineseCalendar();
        lcal.setTimeInMillis(gcal.getTimeInMillis());
        String leap = (lcal.get(ChineseCalendar.IS_LEAP_MONTH) == 1) ? "평달" : "윤달";
		System.out.println("음력 " 
				+ (lcal.get(ChineseCalendar.EXTENDED_YEAR) - 2637) + "년 "
				+ (lcal.get(ChineseCalendar.MONTH        ) + 1   ) + "월 "
				+ lcal.get(ChineseCalendar.DAY_OF_MONTH ) + "일 " 
				+ leap
		);
		
		// 음력간지 - SexagenaryCalendar
		cal.convert();
		System.out.println(cal.CYear + "年 " + cal.CMonth + "月 " + cal.CDay + "日 " + cal.CHour + "時");
		

		// 합삭, 망 - LunarConjunction();
		LunarConjunction lunar = new LunarConjunction();
		Calendar cal_lunar;
		cal_lunar = lunar.getPreviousConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("이번달 합삭시(초하루) : " + sdf.format(cal_lunar.getTime()));
		
		cal_lunar = lunar.getFullMoon(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("이번달 망시간(보름날) : " + sdf.format(cal_lunar.getTime()));
		
		cal_lunar = lunar.getNextConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("다음달 합삭시(초하루) : " + sdf.format(cal_lunar.getTime()));
	}

}
