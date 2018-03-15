package utils;

import java.util.Calendar;

public class CalendarMonthPair {
	public final Calendar calendar;
	public CalendarMonthPair(Calendar calendar) {
		this.calendar=calendar;
	}
	
	@Override
	public String toString() {
		return  convertToMonth(calendar.get(Calendar.MONTH))+ " "+calendar.get(Calendar.YEAR);
	}
	
	public String convertToMonth(int month) {
		switch(month) {
			case Calendar.JANUARY:
				return "JANUARY";
			case Calendar.FEBRUARY:
				return "FEBRUARY";
			case Calendar.MARCH:
				return "MARCH";
			case Calendar.APRIL:
				return "APRIL";
			case Calendar.MAY:
				return "MAY";
			case Calendar.JUNE:
				return "JUNE";
			case Calendar.JULY:
				return "JULY";
			case Calendar.AUGUST:
				return "AUGUST";
			case Calendar.SEPTEMBER:
				return "SEPTEMBER";
			case Calendar.OCTOBER:
				return "OCTOBER";
			case Calendar.NOVEMBER:
				return "NOVEMBER";
			case Calendar.DECEMBER:
				return "DECEMBER";
			default:
				return "";
			
		}
	}
}
