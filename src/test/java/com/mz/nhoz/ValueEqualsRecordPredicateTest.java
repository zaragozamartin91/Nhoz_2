package com.mz.nhoz;

import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.RecordPredicate;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordPredicateException;

import dummy.PersonRecord;
import junit.framework.TestCase;

public class ValueEqualsRecordPredicateTest extends TestCase {

	public void testTest() throws RecordPredicateException, RecordBuilderException {
		Map<String, Object> recMap = new PersonRecord(1, "martin", 123.456, 25).toMap();
		Record record = new RecordBuilder(recMap).build();

		RecordPredicate predicate = new ValueEqualsRecordPredicate(recMap);
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsRecordPredicate("SALARY", new Double(123.456));
		assertTrue(predicate.test(record));

		predicate = new ValueEqualsRecordPredicate().add("ID", new Integer(1)).add("AGE", new Integer(25));
		assertTrue(predicate.test(record));
		
		predicate = new ValueEqualsRecordPredicate("NAME", new String("marton"));
		assertFalse(predicate.test(record));
		
		
	}

}
