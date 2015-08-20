package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

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
		Record protoRecord = rb.build();

		rb = new RecordBuilder(protoRecord);
		String ss = "Como Estas";
		Integer ii = new Integer(99);
		Double dd = new Double(564.12);
		Float ff = new Float(987.45);

		rb.put("key_5", ss);
		rb.put("key_6", ii);
		rb.put("key_7", dd);
		rb.put("key_8", ff);
		Record record = rb.build();

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

	public void testBuild() throws RecordBuilderException {
		String s = "Hola";
		Integer i = new Integer(17);
		Double d = new Double(17.8);
		Float f = new Float(19.99);

		RecordBuilder rb = new RecordBuilder();
		rb.put("key_1", s);
		rb.put("key_2", i);
		rb.put("key_3", d);
		rb.put("key_4", f);
		Record record = rb.build();

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
		record = rb.build();
		numberValue = record.getNumberValue("key_3");
		assertEquals(dd, numberValue);

		stringValue = record.getStringValue("key_1");
		assertEquals(s, stringValue);
		numberValue = record.getNumberValue("key_2");
		assertEquals(i, numberValue);
		numberValue = record.getNumberValue("key_4");
		assertEquals(f, numberValue);
	}

	public void testPutAll() throws RecordBuilderException, RecordUtilsException {
		Record record = new RecordBuilder().put("name", "martin").put("salary", 1234.456).put("age", 25).build();

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "martin");
		values.put("salary", 1234.456);
		values.put("age", 25);
		Record record_2 = new RecordBuilder().putAll(values).build();

		assertTrue(RecordUtils.equals(record, record_2));

		Record record_3 = new RecordBuilder(record).put("job", "programmer").put("height", new Float(1.72)).build();
		values = new HashMap<String, Object>();
		values.put("job", "programmer");
		values.put("height", new Float(1.72));
		Record record_4 = new RecordBuilder(record_2).putAll(values).build();
		
		assertTrue( RecordUtils.equals(record_3, record_4) );
	}

	public void testPutAllEmpty() throws RecordBuilderException, RecordUtilsException {
		Record record = new RecordBuilder().put("name", "martin").put("salary", 1234.456).put("age", 25).build();

		RecordBuilder rb = new RecordBuilder(record);
		HashMap<String, Object> emptyMap = new HashMap<String, Object>();
		rb.putAll(emptyMap);

		assertTrue(RecordUtils.equals(record, rb.build()));
	}
}
