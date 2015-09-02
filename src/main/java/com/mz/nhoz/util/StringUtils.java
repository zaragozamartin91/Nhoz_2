package com.mz.nhoz.util;

import java.util.StringTokenizer;

public class StringUtils {
	private StringUtils() {
	}

	public static boolean nullOrEmpty(String s) {
		return s == null || s.contentEquals("");
	}

	public static boolean nullOrEmpty(Object o) {
		return o == null || o.toString().contentEquals("");
	}

	public static boolean notNullNorEmpty(String s) {
		return s != null && !s.contentEquals("");
	}

	public static boolean empty(String s) {
		return s.contentEquals("");
	}

	public static StringPair parsePair(String line, String delim) {
		StringTokenizer stringTokenizer = new StringTokenizer(line, delim);
		String first = stringTokenizer.nextToken().trim();
		String second = stringTokenizer.nextToken().trim();

		return new StringPair(first, second);
	}

	/**
	 * Intenta comparar dos objetos como strings aplicando trim antes de
	 * comparar.
	 * 
	 * @param first
	 *            - Primer objeto.
	 * @param second
	 *            - Segundo objeto.
	 * @return Si el contenido de <code>first.toString().trim()</code> coincide
	 *         con el de <code>second.toString().trim()</code> , false en caso
	 *         contrario.
	 */
	public static boolean tryCompareObjectsAsStrings(Object first, Object second) {
		try {
			final String s_recordValue = first.toString().trim();
			final String s_compareValue = second.toString().trim();

			return s_compareValue.contentEquals(s_recordValue);
		} catch (Exception e) {
			return false;
		}
	}// tryCompareObjectsAsStrings
}
