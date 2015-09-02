package com.mz.nhoz.util;

import java.text.NumberFormat;
import java.util.Locale;

import com.mz.nhoz.util.exception.NumberUtilsException;

public class NumberUtils {
	public static Number parseUsLocaleNumberStringAsNumber(String s) throws NumberUtilsException {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse(s);

			return number;
		} catch (Exception e) {
			throw new NumberUtilsException(e);
		}
	}

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
			return parseUsLocaleNumberStringAsNumber(s).intValue();
		} catch (Exception e) {
			throw new NumberUtilsException(e);
		}
	}

	/**
	 * Obtiene el valor numerico con formato americano de un String como un
	 * Double. Ejemplo: parseUsLocaleNumberStringAsInteger("123.89") = new
	 * Double(123.89).
	 * 
	 * @param s
	 *            - String a parsear como numero.
	 * @return Integer con valor representativo.
	 * @throws NumberUtilsException
	 */
	public static Double parseUsLocaleNumberStringAsDouble(String s) throws NumberUtilsException {
		try {
			return parseUsLocaleNumberStringAsNumber(s).doubleValue();
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

	/**
	 * Intenta comparar dos objetos como numeros enteros.
	 * 
	 * @param first
	 *            - Primer objeto.
	 * @param second
	 *            - Segundo objeto.
	 * @return True en caso que los objetos puedan representarse como numeros
	 *         enteros y coincidan en valor, false en caso contrario.
	 */
	public static boolean tryCompareObjectsAsIntegers(Object first, Object second) {
		try {
			final Integer n_first = parseUsLocaleNumberStringAsInteger(first.toString().trim());
			final Integer n_second = parseUsLocaleNumberStringAsInteger(second.toString().trim());
			return n_first.equals(n_second);
		} catch (Exception e) {
			return false;
		}
	}// tryCompareObjectsAsIntegers

	/**
	 * Intenta comparar dos objetos como numeros de doble precision.
	 * 
	 * @param first
	 *            - Primer objeto.
	 * @param second
	 *            - Segundo objeto.
	 * @return True en caso que los objetos puedan representarse como numeros de
	 *         doble precision y coincidan en valor, false en caso contrario.
	 */
	public static boolean tryCompareObjectsAsDoubles(Object first, Object second) {
		try {
			final Double n_first = parseUsLocaleNumberStringAsDouble(first.toString().trim());
			final Double n_second = parseUsLocaleNumberStringAsDouble(second.toString().trim());
			return n_first.equals(n_second);
		} catch (Exception e) {
			return false;
		}
	}// tryCompareObjectsAsIntegers
	
}
