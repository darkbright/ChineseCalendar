package com.saramdl.chinese_calendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class LunarConjunction extends GregorianCalendar {
	int year, month, day, hour, minute; // 음력 초하루
	
	public LunarConjunction() {
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(TimeZone arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(Locale arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(TimeZone arg0, Locale arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(int arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(int arg0, int arg1, int arg2, int arg3, int arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
		// TODO Auto-generated constructor stub
	}

	public LunarConjunction(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		// TODO Auto-generated constructor stub
	}
	
	
	// 특정시점(udate)으로부터 tmin분 떨어진 날짜를 구하기
	public Calendar getDateByMin(long tmin, int year, int month, int day, int hour, int minute) {
		Calendar cal = new GregorianCalendar(year, month-1, day, hour, minute);
		
		long millis = cal.getTimeInMillis() - (tmin * 60 * 1000);
		cal.setTimeInMillis(millis);
		
		return cal;
	}

	/**
	 * 이전 합삭 (이번달 초하루) 시간 구하기
	 * @param syear
	 * @param smonth
	 * @param sday
	 * @return
	 */
	public Calendar getPreviousConjunction(int syear, int smonth, int sday) {
		long dm = disp2days(syear, smonth, sday, 1995, 12, 31);
		double dem = moonsundegree(dm);
		double d = dm;
		double de = dem;
		
		while (de < 13.5) {
		    d = d - 1;
			de = moonsundegree(d);
		}
		
		while (de > 1) {
		    d = d - 0.04166666666;
			de = moonsundegree(d);
		}
		
		while (de < 359.99) {
		    d = d - 0.000694444;
			de = moonsundegree(d);
		}
		
		d = d + 0.375;
		d = d * 1440 ;
		long i = -1 * (int) d;
		Calendar pc = getDateByMin(i, 1995, 12, 31, 0, 0);
		
		year = pc.get(Calendar.YEAR);
	  	month = pc.get(Calendar.MONTH) + 1;
	    day = pc.get(Calendar.DAY_OF_MONTH);
	    hour = pc.get(Calendar.HOUR);
	    minute = pc.get(Calendar.MINUTE);

		return pc;
	}
	
	/**
	 * 다음 합삭(다음달 초하루) 시간 구하기
	 * @param syear
	 * @param smonth
	 * @param sday
	 * @return
	 */
	public Calendar getNextConjunction(int syear, int smonth, int sday) {
		long dm = disp2days(syear, smonth, sday, 1995, 12, 31);
		double dem = moonsundegree(dm);
		double d = dm;
		double de = dem;
		
		while (de < 346.5) {
		  d = d + 1;
		  de = moonsundegree(d);
		}
		
		while (de < 359) {
		  d = d + 0.04166666666;
		  de = moonsundegree(d);
		}
		
		while (de>0.01) {
		  d = d + 0.000694444;
		  de = moonsundegree(d);
		}
		
		double pd = d;
		
		d = d + 0.375;
		d = d * 1440;
		long  i = -1 * (int) d;
		Calendar pc = getDateByMin(i, 1995, 12, 31, 0, 0);
		
		// 입력날짜가 다음달 초하루이면 
		if ( (smonth == pc.get(Calendar.MONTH) + 1) && (sday == pc.get(Calendar.DAY_OF_MONTH)) ) {
			year = pc.get(Calendar.YEAR);
		  	month = pc.get(Calendar.MONTH) + 1;
		    day = pc.get(Calendar.DAY_OF_MONTH);
		    hour = pc.get(Calendar.HOUR);
		    minute = pc.get(Calendar.MINUTE);
		
		    d = pd;
		
		    while (de < 347) {
		    	d = d + 1;
		      	de = moonsundegree(d);
		    }
		    
		    while (de < 359) {
		    	d = d + 0.04166666666;
		    	de = moonsundegree(d);
		    }
		    
		    while (de > 0.01) {
		    	d = d + 0.000694444;
		    	de = moonsundegree(d);
		    }
		
		    d = d + 0.375;
		    d = d * 1440 ;
		    i = -1 * (int) (d);
			pc = getDateByMin(i, 1995, 12, 31, 0, 0);
		}
		
		return pc;	
	}

	public Calendar getFullMoon(int syear, int smonth, int sday) {
		double d = disp2days(year, month, day, 1995, 12, 31); // 음력 초하루
	  	d = d + 12; // 음력 12일
		double de = moonsundegree(d);

		while (de < 166.5) {
		    d = d + 1;
		    de = moonsundegree(d);
		}
	
		while (de < 179) {
			d = d + 0.04166666666;
			de = moonsundegree(d);
		}
	
		while (de < 179.99) {
			d = d + 0.000694444;
			de = moonsundegree(d);
		}
	
		d = d + 0.375;
		d = d * 1440;
		int i = -1 * (int) d;
		Calendar pc = getDateByMin(i, 1995, 12, 31, 0, 0);
		
		return pc;	
	}
	
	
	/**
	 * 태양황경과 달황경의 차이
	 * @param day
	 * @return 0 => 합삭, 180 => 망
	 */
	public static double moonsundegree(double day) {
		// 1996년 기준
		double sl = day * 0.98564736 + 278.956807;		// 평균 황경
		double smin = 282.869498 + 0.00004708 * day;	//근일점 황경
		double sminangle = Math.PI * (sl - smin) / 180 ;		// 근점이각
		double sd = 1.919 * Math.sin(sminangle) + 0.02 * Math.sin(2*sminangle); // 황경차
		double sreal = degreelow(sl + sd) ; 			// 진황경 
		
		double ml = 27.836584 + 13.17639648 * day; 		// 평균황경
		double mmin = 280.425774 + 0.11140356 * day; 	// 근지점 황경
		double mminangle = Math.PI * (ml-mmin) / 180; 	// 근점이각
		double msangle = 202.489407 - 0.05295377 * day; // 교점황경
		double msdangle = Math.PI*(ml-msangle) / 180; 	// 교점이각
		double md = 5.068889 * Math.sin(mminangle) + 0.146111 * Math.sin(2 * mminangle) + 0.01 * Math.sin(3 * mminangle)
		       - 0.238056 * Math.sin(sminangle) - 0.087778 * Math.sin(mminangle + sminangle)  // 황경차
		       + 0.048889 * Math.sin(mminangle - sminangle) - 0.129722 * Math.sin(2 * msdangle)
		       - 0.011111 * Math.sin(2*msdangle - mminangle) - 0.012778 * Math.sin(2 * msdangle + mminangle);
		double mreal = degreelow(ml + md) ; // 진황경 

		return degreelow(mreal - sreal);
	}
	
	/**
	 * 각도를 0~360도 이내로 만듬
	 * @param d
	 * @return
	 */
	public static double degreelow(double d) {
		long i;
		double di;
		
		di = d;
		i = (int) di;
		i = i / 360;
		di = di - (360 * i);
		
		while (di >= 360 || di < 0) {
			if (di > 0) 
				di = di - 360;
			else 
				di = di + 360;
		}
		
		return di;
	}
	
	// y1,m1,d1일부터 y2,m2,d2까지의 일수 계산
	public static long disp2days(int year1, int month1, int day1, int year2, int month2, int day2) {
		Calendar date1 = new GregorianCalendar(year1, month1 - 1, day1);
		Calendar date2 = new GregorianCalendar(year2, month2 - 1, day2);
		
		long millis = date1.getTimeInMillis() - date2.getTimeInMillis(); 
		
	    return millis / (24 * 60 * 60 * 1000);
	}

}
