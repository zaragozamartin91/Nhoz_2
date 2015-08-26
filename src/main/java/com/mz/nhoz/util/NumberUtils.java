package com.mz.nhoz.util;

import java.text.NumberFormat;
import java.util.Locale;

import com.mz.nhoz.util.exception.NumberUtilsException;

public class NumberUtils {
	/**
	 * Obtiene el valor numerico con formato americano de un String como un
	 * Integer. Ejemplo: parseUsLocaleNumberStringAsInteger("123.89") = new
	 * Integer(123).
	 * 
	 * @param s
	 *            - String a parsear como numero.
	 * @return Integer con valor representativo.
	 * @throws NumberUtilsException
	 */
	public static Integer parseUsLocaleNumberStringAsInteger(String s) throws NumberUtilsException {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse(s);

			return number.intValue();
		} catch (Exception e) {
			throw new NumberUtilsException(e);
		}
	}

	public static String parseIntegerAsString(Integer integer, int digitCount) {
		String s_integer = integer.toString();

		if (s_integer.length() >= digitCount) {
			return s_integer;
		}

		int diffDigits = digitCount - s_integer.length();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < diffDigits; i++) {
			stringBuilder.append("0");
		}
		stringBuilder.append(s_integer);

		return stringBuilder.toString();
	}
}
