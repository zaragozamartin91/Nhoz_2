package com.mz.nhoz.util;

import java.text.ParseException;

import com.mz.nhoz.util.exception.MoneyUtilsException;

import junit.framework.TestCase;

public class MoneyUtilsTest extends TestCase {

	public void testRemovePriceSymbol() {
		assertEquals("123.345", MoneyUtils.removePriceSymbol("$123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("$    123.345  "));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("  $ 123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("  123.345 "));
		assertEquals("-123.345", MoneyUtils.removePriceSymbol("   -123.345 "));
		assertEquals("-123.345", MoneyUtils.removePriceSymbol("  $ -123.345 "));
	}

	public void testParsePriceAsDouble() throws ParseException, MoneyUtilsException {
		Double d = 123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble("$123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("$    123.345  "));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("  $ 123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("  123.345 "));

		assertEquals(new Double(-123.345), MoneyUtils.parsePriceAsDouble("  $ -123.345 "));
	}

	public void testParsePriceAsDoubleUsinDoubleToString() throws ParseException, MoneyUtilsException {
		Double d = 123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble(d));

		d = -123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble(d));
	}
}
