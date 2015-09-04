package com.mz.nhoz.util;

import com.mz.nhoz.util.exception.NumberUtilsException;

import junit.framework.TestCase;

public class NumberUtilsTest extends TestCase {
	public void testparseUsLocaleNumberStringAsInteger() throws NumberUtilsException {
		Integer intval = NumberUtils.parseUsLocaleNumberStringAsInteger("123.76");
		assertEquals(new Integer(123), intval);

		intval = NumberUtils.parseUsLocaleNumberStringAsInteger("123.0");
		assertEquals(new Integer(123), intval);

		intval = NumberUtils.parseUsLocaleNumberStringAsInteger("123");
		assertEquals(new Integer(123), intval);

		assertEquals("123", intval.toString());
	}

	public void testparseUsLocaleNumberStringAsDouble() throws NumberUtilsException {
		Double doubleVal = NumberUtils.parseUsLocaleNumberStringAsDouble("123.76");
		assertEquals(new Double(123.76), doubleVal);

		doubleVal = NumberUtils.parseUsLocaleNumberStringAsDouble("123");
		assertEquals(new Double(123), doubleVal);

		doubleVal = NumberUtils.parseUsLocaleNumberStringAsDouble("123.0");
		assertEquals(new Double(123.0), doubleVal);

		Double d = Double.valueOf("123.25");
		d = Double.valueOf("123.0");
		d = Double.valueOf("123");

		try {
			doubleVal = NumberUtils.parseUsLocaleNumberStringAsDouble("01-001");
			fail();
		} catch (Exception e) {
		}
	}

	public void testparseIntegerAsString() {
		{
			Integer integer = 123;
			int digitCount = 6;
			String s_integer = NumberUtils.parseIntegerAsString(integer, digitCount);

			assertEquals("000123", s_integer);
		}

		{
			Integer integer = 123456;
			int digitCount = 6;
			String s_integer = NumberUtils.parseIntegerAsString(integer, digitCount);

			assertEquals("123456", s_integer);
		}

		{
			Integer integer = 12345678;
			int digitCount = 6;
			String s_integer = NumberUtils.parseIntegerAsString(integer, digitCount);

			assertEquals("12345678", s_integer);
		}
	}

	public void testTryCompareObjectsAsIntegers() {
		String first = new String("1234");
		String second = new String("1234");
		assertTrue(NumberUtils.tryCompareObjectsAsIntegers(first, second));

		first = new String("1234");
		second = new String("001234");
		assertTrue(NumberUtils.tryCompareObjectsAsIntegers(first, second));

		first = new String("01234");
		second = new String("001234");
		assertTrue(NumberUtils.tryCompareObjectsAsIntegers(first, second));

		first = new String("91234");
		second = new String("001234");
		assertFalse(NumberUtils.tryCompareObjectsAsIntegers(first, second));

	}

}
