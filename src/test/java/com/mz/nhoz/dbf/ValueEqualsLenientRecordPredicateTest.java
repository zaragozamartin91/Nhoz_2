package com.mz.nhoz.dbf;

import java.util.Map;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordPredicateException;

import dummy.PersonRecord;

public class ValueEqualsLenientRecordPredicateTest extends TestCase {

	public void testTestWithPerfectValueTypeCompare() throws RecordPredicateException, RecordBuilderException {
		Map<String, Object> recMap = new PersonRecord(1, "martin", 123.456, 25).toMap();
		Record record = new RecordBuilder(recMap).build();

		RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("SALARY", new Double(123.456));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("ID", new Integer(1)).put("AGE", new Integer(25));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("NAME", new String("marton"));
		assertFalse(predicate.test(record));
	}

	public void testTestWithStringValueTypeCompare() throws RecordPredicateException, RecordBuilderException {
		Map<String, Object> recMap = new PersonRecord(1, "martin", 123.456, 25).toMap();
		Record record = new RecordBuilder(recMap).build();

		RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new String("123.456"));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new String("00123.456"));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new String("123.456"));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new String("123"));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new Integer(123));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new Double(123));
		assertTrue(predicate.test(record));

		predicate = __buildLenientPredicate("SALARY", new Double(123.456));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("ID", new Integer(1)).put("AGE", new Integer(25));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsLenientRecordPredicate("NAME", new String("marton"));
		assertFalse(predicate.test(record));
	}

	public void testTestWithStringValueTypeAndLeadingZeroesCompare() throws RecordPredicateException, RecordBuilderException {
		// 2207D
		{
			Map<String, Object> recMap = new PersonRecord(1, "0000987/F", 123.456, 25).toMap();
			Record record = new RecordBuilder(recMap).build();

			RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
			assertTrue(predicate.test(record));

			predicate = __buildLenientPredicate("NAME", new String("987/F"));
			assertTrue(predicate.test(record));

			predicate = __buildLenientPredicate("NAME", new String("00987/F"));
			assertTrue(predicate.test(record));

			predicate = __buildLenientPredicate("NAME", new String("0000987/F"));
			assertTrue(predicate.test(record));
		}
		
		{
			Map<String, Object> recMap = new PersonRecord(1, "2207", 123.456, 25).toMap();
			Record record = new RecordBuilder(recMap).build();

			RecordPredicate predicate = new ValueEqualsLenientRecordPredicate(recMap);
			assertTrue(predicate.test(record));

			predicate = __buildLenientPredicate("NAME", new String("2207D"));
			boolean test = predicate.test(record);
			assertFalse(test);

		}
	}

	private ValueEqualsLenientRecordPredicate __buildLenientPredicate(String s, Object o) {
		return new ValueEqualsLenientRecordPredicateBuilder(s, o).buildStandard();
	}
}
