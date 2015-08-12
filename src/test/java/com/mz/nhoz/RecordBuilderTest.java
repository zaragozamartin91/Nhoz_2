package com.mz.nhoz;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.exception.RecordBuilderException;

import junit.framework.TestCase;

public class RecordBuilderTest extends TestCase {
	public void testRecordBuilderRecord() throws RecordBuilderException {
		String s = "Hola";
		Integer i = new Integer(17);
		Double d = new Double(17.8);
		Float f = new Float(19.99);

		RecordBuilder rb = new RecordBuilder();
		rb.put("key_1", s);
		rb.put("key_2", i);
		rb.put("key_3", d);
		rb.put("key_4", f);
		Record protoRecord = rb.get();

		rb = new RecordBuilder(protoRecord);
		String ss = "Como Estas";
		Integer ii = new Integer(99);
		Double dd = new Double(564.12);
		Float ff = new Float(987.45);

		rb.put("key_5", ss);
		rb.put("key_6", ii);
		rb.put("key_7", dd);
		rb.put("key_8", ff);
		Record record = rb.get();

		String stringValue = record.getStringValue("key_1");
		assertEquals(s, stringValue);
		Number numberValue = record.getNumberValue("key_2");
		assertEquals(i, numberValue);
		numberValue = record.getNumberValue("key_3");
		assertEquals(d, numberValue);
		numberValue = record.getNumberValue("key_4");
		assertEquals(f, numberValue);
		stringValue = record.getStringValue("key_5");
		assertEquals(ss, stringValue);
		numberValue = record.getNumberValue("key_6");
		assertEquals(ii, numberValue);
		numberValue = record.getNumberValue("key_7");
		assertEquals(dd, numberValue);
		numberValue = record.getNumberValue("key_8");
		assertEquals(ff, numberValue);
		
		rb.put("key_1", ss);
		rb.put("key_2", ii);
		rb.put("key_3", dd);
		rb.put("key_4", ff);
		
		stringValue = protoRecord.getStringValue("key_1");
		assertEquals(s, stringValue);
		numberValue = protoRecord.getNumberValue("key_2");
		assertEquals(i, numberValue);
		numberValue = protoRecord.getNumberValue("key_3");
		assertEquals(d, numberValue);
		numberValue = protoRecord.getNumberValue("key_4");
		assertEquals(f, numberValue);
	}

	public void testGet() throws RecordBuilderException {
		String s = "Hola";
		Integer i = new Integer(17);
		Double d = new Double(17.8);
		Float f = new Float(19.99);

		RecordBuilder rb = new RecordBuilder();
		rb.put("key_1", s);
		rb.put("key_2", i);
		rb.put("key_3", d);
		rb.put("key_4", f);
		Record record = rb.get();

		String stringValue = record.getStringValue("key_1");
		assertEquals(s, stringValue);
		Number numberValue = record.getNumberValue("key_2");
		assertEquals(i, numberValue);
		numberValue = record.getNumberValue("key_3");
		assertEquals(d, numberValue);
		numberValue = record.getNumberValue("key_4");
		assertEquals(f, numberValue);

		Double dd = new Double(987.6544);
		rb.put("key_3", dd);
		record = rb.get();
		numberValue = record.getNumberValue("key_3");
		assertEquals(dd, numberValue);

		stringValue = record.getStringValue("key_1");
		assertEquals(s, stringValue);
		numberValue = record.getNumberValue("key_2");
		assertEquals(i, numberValue);
		numberValue = record.getNumberValue("key_4");
		assertEquals(f, numberValue);
	}
}
