package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormat{

	private static SimpleDateFormat sdf;
	
	public static String formatToString(Date date) {
		if(null == sdf ) {
			sdf = new SimpleDateFormat("MM/dd/yyyy");
		}
		return sdf.format(date);
	}
	
	public static Date formatToDate(String date) throws ParseException {
		if(null == sdf ) {
			sdf = new SimpleDateFormat("MM/dd/yyyy");
		}
		return sdf.parse(date);
	}
	

}
