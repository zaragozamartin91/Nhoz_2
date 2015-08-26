package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

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

	public ValueEqualsRecordPredicate(Map<String, Object> keyValues) {
		super();
		this.keyValues = new HashMap<String, Object>(keyValues);
	}

	public ValueEqualsRecordPredicate(String key, Object value) {
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
	public ValueEqualsRecordPredicate put(String key, Object value) {
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

			Logger.getLogger(getClass()).info("registro "+ recordContent.toString() + " encontrado!");

			return true;
		} catch (RecordUtilsException e) {
			throw new RecordPredicateException(e);
		}
	}

	public String toString() {
		return "" + this.getClass().getName() + "[" + this.keyValues.toString() + "]";
	}
}// ValueEqualsRecordPredicate
