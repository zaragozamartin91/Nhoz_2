package com.mz.nhoz.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MoneyUtils {
	/**
	 * Remueve el simbolo de precio '$' al principio del string.
	 * 
	 * @param s
	 *            - String a parsear.
	 * @return String con precio removido.
	 */
	public static String removePriceSymbol(String s) {
		return removePriceSymbol(s, "$");
	}

	/**
	 * Remueve el simbolo de precio '$' al principio del string.
	 * 
	 * @param s
	 *            - String a parsear.
	 * @return String con precio removido.
	 */
	public static String removePriceSymbol(String s, String symbol) {
		s = s.trim();
		String ss = s.replaceAll("\\" + symbol + " {0,}", "");
		return ss.trim();
	}

	/**
	 * Parsea un string en formato precio como un double.
	 * 
	 * @param s
	 *            - String con formato "$ 1500.25"
	 * @return Valor como Double.
	 * @throws ParseException
	 */
	public static Double parsePriceAsDouble(String s) throws ParseException {
		String ss = removePriceSymbol(s);

		NumberFormat format = NumberFormat.getInstance(Locale.US);
		Number number = format.parse(ss);

		Double d = number.doubleValue();

		return d;
	}

	public static Double parsePriceAsDouble(Object obj) throws ParseException {
		if (obj.getClass().getSuperclass().equals(Number.class)) {
			Number n = (Number) obj;
			return n.doubleValue();
		}

		return parsePriceAsDouble(obj.toString().trim());
	}
}
