package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.RecordPredicateException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;
import com.mz.nhoz.util.NumberUtils;

/**
 * Intenta comparar valores varias veces de varias maneras antes de determinar
 * que son distintos.
 * 
 * @author martin.zaragoza
 *
 */
public class ValueEqualsLenientRecordPredicate implements RecordPredicate {
	private Map<String, Object> keyValues = new HashMap<String, Object>();

	public ValueEqualsLenientRecordPredicate(Map<String, Object> keyValues) {
		super();
		this.keyValues = new HashMap<String, Object>(keyValues);
	}

	public ValueEqualsLenientRecordPredicate(String key, Object value) {
		keyValues.put(key, value);
	}

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
	public ValueEqualsLenientRecordPredicate put(String key, Object value) {
		keyValues.put(key, value);
		return this;
	}

	@Override
	public boolean test(Record record) throws RecordPredicateException {
		try {
			Map<String, Object> recordContent = RecordUtils.deserialize(record);

			Set<String> keySet = keyValues.keySet();

			for (String key : keySet) {
				Object recordValue = recordContent.get(key);
				Object compareValue = keyValues.get(key);

				if (compareValue == null) {
					return false;
				}

				boolean equals = recordValue.equals(compareValue);
				equals = equals || __tryCompareAsIntegers(recordValue, compareValue);
				equals = equals || __tryCompareAsStrings(recordValue, compareValue);

				if (equals == false) {
					return false;
				}
			}

			Logger.getLogger(getClass()).info("registro " + recordContent.toString() + " encontrado!");

			return true;
		} catch (RecordUtilsException e) {
			throw new RecordPredicateException(e);
		}
	}// test

	private boolean __tryCompareAsStrings(Object recordValue, Object compareValue) {
		final String s_recordValue = recordValue.toString().trim();
		final String s_compareValue = compareValue.toString().trim();

		return s_compareValue.contentEquals(s_recordValue);
	}

	private boolean __tryCompareAsIntegers(Object recordValue, Object compareValue) {
		return NumberUtils.tryCompareObjectsAsIntegers(recordValue, compareValue);
	}

	public String toString() {
		return "" + this.getClass().getName() + "[" + this.keyValues.toString() + "]";
	}
}
