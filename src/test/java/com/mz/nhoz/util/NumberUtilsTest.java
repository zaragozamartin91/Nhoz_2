package com.mz.nhoz.util;


import com.mz.nhoz.util.exception.NumberUtilsException;

import junit.framework.TestCase;

public class NumberUtilsTest extends TestCase {
	public void testparseUsLocaleNumberStringAsInteger() throws NumberUtilsException {
		Integer intval = NumberUtils.parseUsLocaleNumberStringAsInteger("123.76");
		assertEquals(new Integer(123), intval);

		intval = NumberUtils.parseUsLocaleNumberStringAsInteger("123.0");
		assertEquals(new Integer(123), intval);

		assertEquals("123", intval.toString());
	}
}
