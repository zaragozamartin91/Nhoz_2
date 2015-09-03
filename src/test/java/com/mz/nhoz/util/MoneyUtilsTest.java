package com.mz.nhoz.util;

import java.text.ParseException;

import junit.framework.TestCase;

public class MoneyUtilsTest extends TestCase {

	public void testRemovePriceSymbol() {
		assertEquals("123.345", MoneyUtils.removePriceSymbol("$123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("$    123.345  "));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("  $ 123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("123.345"));
		assertEquals("123.345", MoneyUtils.removePriceSymbol("  123.345 "));
		assertEquals("-123.345", MoneyUtils.removePriceSymbol("   -123.345 "));
	}

	public void testParsePriceAsDouble() throws ParseException {
		Double d = 123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble("$123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("$    123.345  "));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("  $ 123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("123.345"));
		assertEquals(d, MoneyUtils.parsePriceAsDouble("  123.345 "));
	}

	public void testParsePriceAsDoubleUsinDoubleToString() throws ParseException {
		Double d = 123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble(d));
		
		d = -123.345;
		assertEquals(d, MoneyUtils.parsePriceAsDouble(d));
	}
}
