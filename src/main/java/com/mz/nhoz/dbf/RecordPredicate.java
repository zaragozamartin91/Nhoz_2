package com.mz.nhoz.dbf;

import com.mz.nhoz.dbf.exception.RecordPredicateException;

import nl.knaw.dans.common.dbflib.Record;

public interface RecordPredicate {
	public boolean test(Record record) throws RecordPredicateException;
}
