package com.mz.nhoz.dbf;

import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.RecordTransformerException;

public class ConditionalValueSetRecordTransformer extends ValueSetRecordTransformer {
	private RecordPredicate predicate;

	public ConditionalValueSetRecordTransformer setPredicate(RecordPredicate predicate) {
		this.predicate = predicate;
		return this;
	}

	public ConditionalValueSetRecordTransformer(String key, Object value, RecordPredicate predicate) {
		super(key, value);
		setPredicate(predicate);
	}

	public ConditionalValueSetRecordTransformer() {
		super();
	}

	public ConditionalValueSetRecordTransformer(Map<String, Object> valuesToSet) {
		this(valuesToSet,null);
	}
	
	public ConditionalValueSetRecordTransformer(Map<String, Object> valuesToSet ,RecordPredicate predicate) {
		super(valuesToSet);
		setPredicate(predicate);
	}

	public ConditionalValueSetRecordTransformer(String key, Object value) {
		super(key, value);
	}

	@Override
	public Record transform(Record record) throws RecordTransformerException {
		try {
			if (predicate.test(record)) {
				return super.transform(record);
			} else {
				return record;
			}
		} catch (Exception e) {
			throw new RecordTransformerException(e);
		}
	}// transform
}//ConditionalValueSetRecordTransformer
