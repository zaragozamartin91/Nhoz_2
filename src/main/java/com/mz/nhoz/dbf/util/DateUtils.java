package com.mz.nhoz.dbf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mz.nhoz.dbf.util.exception.DateUtilsException;

public class DateUtils {
	public static Date getDate(String pattern, String s_date) throws DateUtilsException {
		try {
			return new SimpleDateFormat(pattern).parse(s_date);
		} catch (ParseException e) {
			throw new DateUtilsException(e);
		}
	}

	public static Date getToday() {
		return Calendar.getInstance().getTime();
	}
}
