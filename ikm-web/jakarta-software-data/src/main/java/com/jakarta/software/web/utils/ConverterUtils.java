package com.jakarta.software.web.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConverterUtils {

	public static String numberToWord(long number, StringBuffer result) {

		String bilangan[] = new String[] { "", "satu ", "dua ", "tiga ",
				"empat ", "lima ", "enam ", "tujuh ", "delapan ", "sembilan ",
				"sepuluh ", "sebelas " };

		if (number < 12) {
			result.append(bilangan[(int) number]);
		}

		if (number >= 12 && number < 20) {
			numberToWord(number - 10, result);
			result.append("belas ");
		}

		if (number >= 20 && number < 100) {
			numberToWord(number / 10, result);
			result.append("puluh ");
			numberToWord(number % 10, result);
		}

		if (number >= 100 && number < 200) {
			result.append("seratus ");
			numberToWord(number % 100, result);
		}

		if (number >= 200 && number < 1000) {
			numberToWord(number / 100, result);
			result.append("ratus ");
			numberToWord(number % 100, result);
		}

		if (number >= 1000 && number < 2000) {
			result.append("seribu ");
			numberToWord(number % 1000, result);
		}

		if (number >= 2000 && number < 1000000) {
			numberToWord(number / 1000, result);
			result.append("ribu ");
			numberToWord(number % 1000, result);
		}

		if (number >= 1000000 && number < 1000000000) {
			numberToWord(number / 1000000, result);
			result.append("juta ");
			numberToWord(number % 1000000, result);
		}
		// result = result.append("rupiah ");
		return result.toString() + "rupiah ";
	}

	public static int parseInt(char c) {
		int result = c - 48;
		if (result < 0 || result > 9)
			throw new NumberFormatException("For input char: '" + c + "'");
		return result;
	}

	public static String rupiahCurrency(int money) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String rupiahCurr = nf.format(money);
		rupiahCurr = rupiahCurr.replace("$", "Rp ").replaceAll(",", ".");
		rupiahCurr = rupiahCurr.substring(0, rupiahCurr.length() - 3) + ",00";

		return rupiahCurr;
	}

	public static Date startOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 1);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}

	public static Date endOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		return calendar.getTime();
	}

	// FORMAT : "yyyy/MM/dd" (2009-01-31) or "dd/MM/yyyy" (31-01-2009)
	public static Date strToDate(String strDate, String formatDate) {
		Date convertedDate = new Date();

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate);
			convertedDate = dateFormat.parse(strDate);
		} catch (ParseException ex) {
			System.out.println("Error, convert from string to date : " + ex);
		}

		return convertedDate;
	}

	// FORMAT : "yyyy/MM/dd" (2009-01-31) or "dd/MM/yyyy" (31-01-2009)
	public static String dateToStr(Date dateStr, String formatDate) {
		StringBuilder strDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate);
			strDate = new StringBuilder(dateFormat.format(dateStr));
		} catch (Exception e) {
			System.out.println("Error, convert from string to date : " + e);
		}
		return strDate.toString();

	}

	public static int subtractDays(Date date1, Date date2) {
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
//
//		int days1 = 0;
//		int days2 = 0;
//		int maxYear = Math.max(gc1.get(Calendar.YEAR), gc2.get(Calendar.YEAR));
//
//		GregorianCalendar gctmp = (GregorianCalendar) gc1.clone();
//		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
//			days1 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
//			gctmp.add(Calendar.YEAR, 1);
//		}
//
//		gctmp = (GregorianCalendar) gc2.clone();
//		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
//			days2 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
//			gctmp.add(Calendar.YEAR, 1);
//		}
//
//		days1 += gc1.get(Calendar.DAY_OF_YEAR) - 1;
//		days2 += gc2.get(Calendar.DAY_OF_YEAR) - 1;
		
		
		 // Creates two calendars instances
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        
//        // Set the date for both of the calendar instance
//        cal1.set(gc1., 12, 30);
//        cal2.set(2007, 5, 3);

        // Get the represented date in milliseconds
        long milis1 = gc1.getTimeInMillis();
        long milis2 = gc2.getTimeInMillis();
        
        // Calculate difference in milliseconds
        long diff = milis2 - milis1;
        
        // Calculate difference in seconds
        long diffSeconds = diff / 1000;
        
        // Calculate difference in minutes
        long diffMinutes = diff / (60 * 1000);
        
        // Calculate difference in hours
        long diffHours = diff / (60 * 60 * 1000);
        
        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);

		return (int)diffDays;
//		return (days1 - days2);
	}

	public static long subtractTime(String time1, String time2) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		long result = 0;

		try {
			Date s = sdf.parse(time1); // current time
			Date e = sdf.parse(time2); // time in field
			Calendar scal = java.util.GregorianCalendar.getInstance();
			scal.setTime(s);
			Calendar ecal = java.util.GregorianCalendar.getInstance();
			ecal.setTime(e);

			// FOR TIME 00:xx and 12:xx and 24:00
			if (time1.substring(0, 2).equals("12")
					|| time2.substring(0, 2).equals("12")) {

				long hourTime1 = Integer.parseInt(time1.substring(0, 2));
				long hourTime2 = Integer.parseInt(time2.substring(0, 2));
				long minuteTime1 = Integer.parseInt(time1.substring(3, 5));
				long minuteTime2 = Integer.parseInt(time2.substring(3, 5));

				// 12 o'clock & 12 o'clock
				if (hourTime1 == hourTime2) {
					if (minuteTime1 > minuteTime2)
						result = minuteTime1 - minuteTime2;
					else
						result = minuteTime2 - minuteTime1;
				} else if (hourTime1 > hourTime2) {
					// >12 o'clock & 12 o'clock

					if (minuteTime1 >= minuteTime2) {
						result = ((minuteTime1 - minuteTime2) + ((hourTime1 - hourTime2) * 60))
								* -1;
					} else {
						hourTime1 = hourTime1 - 1;
						result = (((minuteTime1 + 60) - minuteTime2) + ((hourTime1 - hourTime2) * 60))
								* -1;
					}
				} else {
					// 12 o'clock & > 12 o'clock

					if (minuteTime2 >= minuteTime1) {
						result = (minuteTime2 - minuteTime1)
								+ ((hourTime2 - hourTime1) * 60);
					} else {
						hourTime2 = hourTime2 - 1;
						result = ((minuteTime2 + 60) - minuteTime1)
								+ ((hourTime2 - hourTime1) * 60);
					}
				}
				return result;
			} else {
				result = (e.getTime() - s.getTime()) / 60000;
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
