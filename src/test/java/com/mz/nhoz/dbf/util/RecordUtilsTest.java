package com.mz.nhoz.dbf.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import junit.framework.TestCase;

public class RecordUtilsTest extends TestCase {

	public void testDeserializeRecord() throws RecordUtilsException, RecordBuilderException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", new String("martin"));
		map.put("age", new Integer(25));
		map.put("salary", new Double(1234.654));

		RecordBuilder rb = new RecordBuilder(map);
		Record record = rb.build();
		Map<String, Object> __map = RecordUtils.deserialize(record);

		assertEquals(__map, map);

		__map = RecordUtils.deserialize(rb.put("id", new Float(35657201)).build());
		assertNotSame(__map, map);
	}

	public void testRecordEquals() throws RecordBuilderException, RecordUtilsException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", new String("martin"));
		map.put("age", new Integer(25));
		map.put("salary", new Double(1234.654));

		RecordBuilder rb_1 = new RecordBuilder();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			rb_1.put(key, map.get(key));
		}
		Record record_1 = rb_1.build();
		
		Record record_2 = new RecordBuilder(map).build();
		
		assertTrue( RecordUtils.equals(record_1, record_2) );
	}
	
	public void testRecordEqualsSameRecord() throws RecordBuilderException, RecordUtilsException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", new String("martin"));
		map.put("age", new Integer(25));
		map.put("salary", new Double(1234.654));

		RecordBuilder rb_1 = new RecordBuilder();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			rb_1.put(key, map.get(key));
		}
		Record record_1 = rb_1.build();
		
		Record record_2 = record_1;
		
		assertTrue( record_1.equals(record_2) && RecordUtils.equals(record_1, record_2) );
	}
}
