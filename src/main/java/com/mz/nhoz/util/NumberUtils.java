package com.mz.nhoz.util;

import java.text.NumberFormat;
import java.util.Locale;

import com.mz.nhoz.util.exception.NumberUtilsException;


public class NumberUtils {
	public static Integer parseUsLocaleNumberStringAsInteger(String s) throws NumberUtilsException {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse(s);
			
			return number.intValue();
		} catch (Exception e) {
			throw new NumberUtilsException(e);
		}
	}
}
