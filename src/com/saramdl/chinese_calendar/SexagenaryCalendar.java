package com.saramdl.chinese_calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SexagenaryCalendar {
	public static int GYear, GMonth, GDay, GHour, GMinute; 		// Gregorian Calendar
	public static String CYear, CMonth, CDay, CHour, CMinute; 	// Chinese Calendar
	public static Boolean large_month;

	// ܰ�� ���ٿ� ��ڱ�� ������ - ����
	static int unityear = 1996;
	static int unitmonth = 2;
	static int unitday = 4;
	static int unithour = 22;
	static int unitmin = 8;

	static String ganji[] = {
			"ˣ�", "����", "ܰ��", "����", "����", "����", "����", "��ڱ", "����", "ͤ�",
			"ˣ��", "����", "ܰ�", "����", "����", "����", "����", "����", "����", "ͤڱ",
			"ˣ��", "���", "ܰ��", "����", "���", "����", "����", "����", "����", "ͤ��",
			"ˣ��", "��ڱ", "ܰ��", "���", "����", "����", "���", "����", "����", "ͤ��",
			"ˣ��", "����", "ܰ��", "��ڱ", "����", "���", "����", "����", "���", "ͤ��",
			"ˣ��", "����", "ܰ��", "����", "����", "��ڱ", "����", "���", "����", "ͤ��"
	};

	static int month_array[] = {0, 21355, 42843, 64498, 86335, 108366, 130578, 152958,
			175471, 198077, 220728, 243370, 265955, 288432, 310767, 332928,
			354903, 376685, 398290, 419736, 441060, 462295, 483493, 504693, 525949};

	private static int cYear, cMonth, cDay, cHour;
	private static int displToMin;
	private static int displToDay;


	public SexagenaryCalendar() {
		super();
		// TODO Auto-generated construct|| stub
	}

	public SexagenaryCalendar(int gYear, int gMonth, int gDay) {
		super();
		GYear = gYear;
		GMonth = gMonth;
		GDay = gDay;

		convert();
	}
	
	// �׷����� ����� --> �����¾�� ��,��,��, ��/��, ��/��
	public void SolarToLunar(int solyear, int solmon, int solday) {
	}
	
	// �׷����� ����Ͻú� --> �ش����ڰ� ����ִ� ��������,�߱�,��������
	public static void SolarToSo24(int sol_year, int sol_month, int sol_day, int sol_hour, int sol_minute) {
	}
	

	/**
	 * year ���� 1�� 1�Ϻ��� year�� month�� day�ϱ����� ���� ���
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static int dispTimeDay(int year, int month, int day) {
		int e = 0;

		for (int i = 1; i < month; i++) {
			e = e + 31;
			if (i == 2 || i == 4 ||i == 6 ||i == 9 ||i == 11)
				e = e - 1;
			if (i == 2) {
				e = e - 2;
				if (year % 4 == 0) e = e + 1;
				if (year % 100 == 0) e = e - 1;
				if (year % 400 == 0) e = e + 1;
				if (year % 4000 == 0) e = e - 1;
			}
		}

		return e + day;
	}

	private static int dispToDays(int y1, int m1, int d1, int y2, int m2, int d2) {
		int p1, p2, p1n, pp1,pp2,pr,i,dis,ppp1,ppp2;

		if (y2 > y1) {
			p2 = dispTimeDay(y2, m2, d2);
			p1 = dispTimeDay(y1, m1, d1);
			p1n = dispTimeDay(y1, 12, 31);
			pp1 = y1;
			pp2 = y2;
			pr = -1;
		} else {
			p1 = dispTimeDay(y2, m2, d2);
			p1n = dispTimeDay(y2, 12, 31);
			p2 = dispTimeDay(y1, m1, d1);
			pp1 = y2;
			pp2 = y1;
			pr = +1;
		}

		if (y2 == y1)
			dis = p2 - p1;
		else {
			dis = p1n - p1 ;
			ppp1 = pp1 + 1 ;
			ppp2 = pp2 - 1 ;

			i = dis;

			while (ppp1 <= ppp2) {
				i = i + dispTimeDay(ppp1, 12, 31);
				ppp1 = ppp1 + 1;
			}
			dis = i;

			dis = dis + p2;
			dis = dis * pr;
		}

		return dis;
	}

	private static int getMinByTime(int uy, int umm, int ud, int uh, int umin, int y1, int mo1, int d1, int h1, int mm1) {
		int disp_day = dispToDays(uy, umm, ud, y1, mo1, d1);
		int t = disp_day * 24 * 60 + (uh-h1) * 60 + (umin - mm1);
		return t;
	}

	/**
	 * ���� ���ϱ�
	 * @param displToMin
	 * @return
	 */
	private static int convert_year(long displToMin) {
		int so24 = (int) (displToMin / 525949);
 
		if (displToMin >= 0)
			so24 = so24 + 1;
		
		int cYear = -1 * (so24 % 60);
		cYear = cYear + 12;
		if (cYear < 0) cYear = cYear + 60;
		if (cYear > 59) cYear = cYear - 60;

		return cYear;
	}

	/**
	 * ���� ���ϱ�
	 * @param displToMin
	 * @param cYear			����
	 * @return				����
	 */
 	private static int convert_month(long displToMin, int cYear) {
		int monthmin100 = (int) (525949 - (displToMin % 525949));

		if (monthmin100 < 0) monthmin100 = monthmin100 + 525949;
		if (monthmin100 >= 525949) monthmin100 = monthmin100 - 525949;

		int i, j, t;
		int cMonth = 0;

		for (i = 0; i < 12; i++) {
			j = i * 2;
			if (month_array[j] <= monthmin100 && monthmin100 < month_array[j+2])
				cMonth = i;
		}

		i = cMonth;
		t = cYear % 10;
		t = t % 5;
		t = t * 12 + 2 + i;
		cMonth = t;
		if (cMonth > 59) cMonth = cMonth - 60;

		return cMonth;
	}

	// ���� ���ϱ�
	private static int convert_day(long displToDay) {
		int cDay = (int) (displToDay % 60);
		cDay = -1 * cDay;
		cDay = cDay + 7;

		if (cDay < 0)
			cDay = cDay + 60;
		if (cDay > 59)
			cDay = cDay - 60;

		return cDay;
	}

	// ���� ���ϱ�
	private static int convert_hour(int GHour, int cDay) {
		int h = (GHour + 1) / 2;
		int cHour = cDay % 10;
		cHour = cHour % 5;
		cHour = cHour * 12 + h;

		return cHour;
	}

	public static int convert() {
		displToMin = getMinByTime(unityear, unitmonth, unitday, unithour, unitmin, GYear, GMonth, GDay, GHour, GMinute);
		displToDay = dispToDays(unityear, unitmonth, unitday, GYear, GMonth, GDay);

		cYear = convert_year(displToMin);
		CYear = ganji[cYear];
		cMonth = convert_month(displToMin, cYear);
		CMonth = ganji[cMonth];
		convert_day(displToDay);
		CDay = ganji[cDay];
		convert_hour(GHour, cDay);
		CHour = ganji[cHour];

		return cYear;
	}
	
}
