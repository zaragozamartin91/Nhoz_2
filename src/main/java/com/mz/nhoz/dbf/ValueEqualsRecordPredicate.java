package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mz.nhoz.dbf.exception.RecordPredicateException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import nl.knaw.dans.common.dbflib.Record;

/**
 * Predicado de verificacion de valores de un registro.
 * 
 * @author martin.zaragoza
 *
 */
public class ValueEqualsRecordPredicate implements RecordPredicate {
	private Map<String, Object> keyValues = new HashMap<String, Object>();

	/**
	 * Agregar valor a verificar.
	 * 
	 * @param key
	 *            - id del valor a testear dentro del registro.
	 * @param value
	 * 
	 *            - valor a testear.
	 * @return this.
	 */
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
				Object recordValue = recordContent.get(key);
				Object compareValue = keyValues.get(key);

				/*
				 * si el tipo de valor del registro es String -> comparo ambos
				 * valores como Strings
				 */
				if (recordValue.getClass().equals(String.class)) {
					recordValue = recordValue.toString().trim();
					compareValue = compareValue.toString().trim();
				}

				if (compareValue.equals(recordValue) == false) {
					return false;
				}
			}
		} catch (RecordUtilsException e) {
			throw new RecordPredicateException(e);
		}

		return true;
	}
}// ValueEqualsRecordPredicate
