package com.mz.nhoz;

import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Value;

import com.mz.nhoz.exception.ValueParserException;

import dummy.Dummy;
import junit.framework.TestCase;

public class ValueParserTest extends TestCase {
	ValueSerializer vp = new ValueSerializer();

	public void testParse() {
		Object rawVal;
		rawVal = new Integer(25);
		try {
			Value val = vp.parse(rawVal);
			assertEquals(val.getClass(), NumberValue.class);
		} catch (ValueParserException e) {
			e.printStackTrace();
			fail();
		}

		rawVal = new Double(25.99);
		try {
			Value val = vp.parse(rawVal);
			assertEquals(val.getClass(), NumberValue.class);
		} catch (ValueParserException e) {
			e.printStackTrace();
			fail();
		}

		rawVal = new Float(12.3456);
		try {
			Value val = vp.parse(rawVal);
			assertEquals(val.getClass(), NumberValue.class);
		} catch (ValueParserException e) {
			e.printStackTrace();
			fail();
		}

		rawVal = new String("hello");
		try {
			Value val = vp.parse(rawVal);
			assertEquals(val.getClass(), StringValue.class);
		} catch (ValueParserException e) {
			e.printStackTrace();
			fail();
		}

		/*si al value parser se le pasa una instancia de value, devuelve la misma instancia.*/
		rawVal = new StringValue("some value");
		try {
			Value val = vp.parse(rawVal);
			assertEquals(val, rawVal);
		} catch (ValueParserException e) {
			e.printStackTrace();
			fail();
		}

		rawVal = new Dummy("hello", 123);
		try {
			vp.parse(rawVal);
			fail();
		} catch (ValueParserException e) {
		}

	}
}// ValueParserTest
