package com.mz.nhoz.util;

import java.util.StringTokenizer;

public class StringUtils {
	private StringUtils() {
	}

	public static boolean nullOrEmpty(String s) {
		return s == null || s.contentEquals("");
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
}
