package com.mz.nhoz.dbf;

import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.RecordPredicate;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordPredicateException;

import dummy.PersonRecord;
import junit.framework.TestCase;

public class ValueEqualsLenientRecordPredicateTest extends TestCase {

	public void testTestWithPerfectValueTypeCompare() throws RecordPredicateException, RecordBuilderException {
		Map<String, Object> recMap = new PersonRecord(1, "martin", 123.456, 25).toMap();
		Record record = new RecordBuilder(recMap).build();

		RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsRecordPredicate("SALARY", new Double(123.456));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsRecordPredicate("ID", new Integer(1)).put("AGE", new Integer(25));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsRecordPredicate("NAME", new String("marton"));
		assertFalse(predicate.test(record));
	}
	
	public void testTestWithStringValueTypeCompare() throws RecordPredicateException, RecordBuilderException {
		Map<String, Object> recMap = new PersonRecord(1, "martin", 123.456, 25).toMap();
		Record record = new RecordBuilder(recMap).build();

		RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new String("123.456"));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new String("00123.456"));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new String("123.456"));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new String("123"));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new Integer(123));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new Double(123));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new Double(123.456));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("ID", new Integer(1)).put("AGE", new Integer(25));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("NAME", new String("marton"));
		assertFalse(predicate.test(record));
	}

}
