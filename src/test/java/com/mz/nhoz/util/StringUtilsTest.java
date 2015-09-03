package com.mz.nhoz.util;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	public void testRemovePriceSymbol() {
		assertEquals("123.345",  StringUtils.removePriceSymbol( "$123.345" ) );
		assertEquals("123.345",  StringUtils.removePriceSymbol( "$    123.345  " ) );
		assertEquals("123.345",  StringUtils.removePriceSymbol( "  $ 123.345" ) );
		assertEquals("123.345",  StringUtils.removePriceSymbol( "123.345" ) );
		assertEquals("123.345",  StringUtils.removePriceSymbol( "  123.345 " ) );
	}

	public void testParsePriceAsDouble() {
		
	}
}
