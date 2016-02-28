package com.mz.nhoz.util;

import java.text.ParseException;
import java.util.Locale;

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

	public void testReplaceCommaWithDot() throws MoneyUtilsException {
		assertEquals("-123.456", MoneyUtils.replaceCommaWithDot("-123,456"));
		assertEquals("-123.456", MoneyUtils.replaceCommaWithDot("-123.456"));
		assertEquals("  $ -123.456", MoneyUtils.replaceCommaWithDot("  $ -123,456"));
		
		assertEquals("123.456", MoneyUtils.replaceCommaWithDot("123,456"));
		assertEquals("123.456", MoneyUtils.replaceCommaWithDot("123.456"));
		assertEquals("  $ 123.456", MoneyUtils.replaceCommaWithDot("  $ 123,456"));
		
		assertEquals("123", MoneyUtils.replaceCommaWithDot("123"));
		assertEquals("123", MoneyUtils.replaceCommaWithDot("123"));
		assertEquals("  $ 123", MoneyUtils.replaceCommaWithDot("  $ 123"));
	}
	
	public void testParseWithDifferentLocales() throws MoneyUtilsException{
		assertEquals( new Double(1234.23) , MoneyUtils.parsePriceAsDouble("$1,234.23", Locale.US) );
		assertEquals( new Double(19234.23) , MoneyUtils.parsePriceAsDouble("$   19,234.23", Locale.US) );
		
		assertEquals( new Double(1234.23) , MoneyUtils.parsePriceAsDouble("$1.234,23", Locale.ITALY) );
		assertEquals( new Double(19234.23) , MoneyUtils.parsePriceAsDouble("$   19.234,23", Locale.ITALY) );
	}
}
