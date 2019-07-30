package com.medlife.deliveries.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class DateUtils {
	
	public static Long getCurrentTimestampInSeconds() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Long timestamp = cal.getTimeInMillis()/1000;
		return timestamp;
	}

	public static Long getStartOfTheDayTimestamp() {
		ZoneId z = ZoneId.of( "UTC" );
		ZonedDateTime zdt = ZonedDateTime.now( z );
		LocalDate today = zdt.toLocalDate();
		ZonedDateTime zdtTodayStart = today.atStartOfDay( z );
		return zdtTodayStart.toEpochSecond();
	}

	public static String  getCurrentTimeInIstFormattedForClickpost(){
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				.format(now);
	}
}
