package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.RecordTransformerException;

public class ValueSetRecordTransformer implements RecordTransformer {
	private Map<String, Object> valuesToSet = new HashMap<String, Object>();

	public ValueSetRecordTransformer(String key, Object value) {
		super();
		this.add(key, value);
	}

	public ValueSetRecordTransformer(Map<String, Object> valuesToSet) {
		super();
		this.valuesToSet = valuesToSet;
	}

	public ValueSetRecordTransformer() {
		super();
	}

	public ValueSetRecordTransformer add(String key, Object value) {
		valuesToSet.put(key, value);
		return this;
	}

	@Override
	public Record transform(Record record) throws RecordTransformerException {
		try {
			RecordBuilder rb = new RecordBuilder(record);
			rb.putAll(valuesToSet);
			return rb.build();
		} catch (Exception e) {
			throw new RecordTransformerException(e);
		}
	}//transform
}//ValueSetRecordTransformer
