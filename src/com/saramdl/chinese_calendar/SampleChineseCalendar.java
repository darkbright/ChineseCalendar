package com.saramdl.chinese_calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.ibm.icu.util.ChineseCalendar;

public class SampleChineseCalendar {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy�� M�� dd�� E���� HH�� mm��");
		
		Calendar gcal = new GregorianCalendar();
		SexagenaryCalendar cal = new SexagenaryCalendar();
		
		cal.GYear 	= gcal.get(Calendar.YEAR);
		cal.GMonth 	= gcal.get(Calendar.MONTH) + 1;
		cal.GDay 	= gcal.get(Calendar.DAY_OF_MONTH);
		cal.GHour 	= gcal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		cal.GMinute = gcal.get(Calendar.MINUTE);
		
		System.out.println("��� " + sdf.format(gcal.getTime()));
		
        //���->���� ��ȯ
        ChineseCalendar lcal = new ChineseCalendar();
        lcal.setTimeInMillis(gcal.getTimeInMillis());
        String leap = (lcal.get(ChineseCalendar.IS_LEAP_MONTH) == 1) ? "���" : "����";
		System.out.println("���� " 
				+ (lcal.get(ChineseCalendar.EXTENDED_YEAR) - 2637) + "�� "
				+ (lcal.get(ChineseCalendar.MONTH        ) + 1   ) + "�� "
				+ lcal.get(ChineseCalendar.DAY_OF_MONTH ) + "�� " 
				+ leap
		);
		
		// ���°��� - SexagenaryCalendar
		cal.convert();
		System.out.println(cal.CYear + "Ҵ " + cal.CMonth + "�� " + cal.CDay + "�� " + cal.CHour + "��");
		

		// �ջ�, �� - LunarConjunction();
		LunarConjunction lunar = new LunarConjunction();
		Calendar cal_lunar;
		cal_lunar = lunar.getPreviousConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("�̹��� �ջ��(���Ϸ�) : " + sdf.format(cal_lunar.getTime()));
		
		cal_lunar = lunar.getFullMoon(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("�̹��� ���ð�(������) : " + sdf.format(cal_lunar.getTime()));
		
		cal_lunar = lunar.getNextConjunction(cal.GYear, cal.GMonth, cal.GDay);
		System.out.println("������ �ջ��(���Ϸ�) : " + sdf.format(cal_lunar.getTime()));
	}

}
