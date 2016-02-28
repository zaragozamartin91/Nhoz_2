package com.mz.nhoz.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mz.nhoz.util.exception.MoneyUtilsException;

public class MoneyUtils {
	public final static String S_MONEY_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
	public final static String S_LENIENT_REGEX = "[-+]?[0-9]*[\\.,]?[0-9]+[\\.,]?[0-9]*";

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
	 * @throws MoneyUtilsException
	 */
	public static Double parsePriceAsDouble(String s) throws MoneyUtilsException {
		// String ss = removePriceSymbol(s);
		//
		// NumberFormat format = NumberFormat.getInstance(Locale.US);
		// Number number = format.parse(ss);
		//
		// Double d = number.doubleValue();
		//
		// return d;

		Matcher matcher = Pattern.compile(S_MONEY_REGEX).matcher(s);
		if (matcher.find()) {
			String match = matcher.group();
			return Double.valueOf(match);
		} else {
			throw new MoneyUtilsException("Error al obtener valor dinero a partir de " + s + " usando " + S_MONEY_REGEX);
		}
	}

	public static String replaceCommaWithDot(String s) throws MoneyUtilsException {
		try {
			return s.replaceAll(",", "\\.");
		} catch (Exception e) {
			throw new MoneyUtilsException(e);
		}
	}

	/**
	 * Intenta obtener un valor Double a partir de un objeto. El objeto puede
	 * ser de tipo Numerico en cuyo caso se retorna doubleValue, o puede ser una
	 * expresion de numero en forma de String como "123.45" o "$ 654.32".
	 * 
	 * @param obj
	 *            - Objeto a parsear.
	 * @return Valor como Double.
	 * @throws ParseException
	 * @throws MoneyUtilsException
	 */
	public static Double parsePriceAsDouble(Object obj) throws MoneyUtilsException {
		if (obj.getClass().getSuperclass().equals(Number.class)) {
			Number n = (Number) obj;
			return n.doubleValue();
		} else {
			String s = obj.toString().trim();
			s = replaceCommaWithDot(s);
			return parsePriceAsDouble(s);
		}
	}

	public static Double parsePriceAsDouble(Object obj, Locale locale) throws MoneyUtilsException {
		if (obj.getClass().getSuperclass().equals(Number.class)) {
			Number n = (Number) obj;
			return n.doubleValue();
		}
		
		String s = obj.toString().trim();
		Matcher matcher = Pattern.compile(S_LENIENT_REGEX).matcher(s);
		if (matcher.find()) {
			String match = matcher.group();

			NumberFormat format = NumberFormat.getInstance(locale);

			try {
				Number number = format.parse(match);
				double d = number.doubleValue();
				return d;
			} catch (Exception e) {
				throw new MoneyUtilsException(e);
			}
		} else {
			throw new MoneyUtilsException("Error al obtener valor dinero a partir de " + s + " usando " + S_LENIENT_REGEX);
		}

	}
}
