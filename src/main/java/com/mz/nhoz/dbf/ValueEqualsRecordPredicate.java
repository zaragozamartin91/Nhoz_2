package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mz.nhoz.dbf.exception.RecordPredicateException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import nl.knaw.dans.common.dbflib.Record;

public class ValueEqualsRecordPredicate implements RecordPredicate {
	private Map<String, Object> keyValues = new HashMap<String, Object>();

	public ValueEqualsRecordPredicate add(String key, Object value) {
		keyValues.put(key, value);
		return this;
	}

	public ValueEqualsRecordPredicate(Map<String, Object> keyValues) {
		super();
		this.keyValues = new HashMap<String, Object>(keyValues);
	}

	public ValueEqualsRecordPredicate(String key, Object value) {
		keyValues.put(key, value);
	}
	
	public ValueEqualsRecordPredicate() {
		super();
	}

	@Override
	public boolean test(Record record) throws RecordPredicateException {
		try {
			Map<String, Object> recordContent = RecordUtils.deserialize(record);

			Set<String> keySet = keyValues.keySet();

			for (String key : keySet) {
				if (keyValues.get(key).equals(recordContent.get(key)) == false) {
					return false;
				}
			}
		} catch (RecordUtilsException e) {
			throw new RecordPredicateException(e);
		}

		return true;
	}
}// ValueEqualsRecordPredicate
