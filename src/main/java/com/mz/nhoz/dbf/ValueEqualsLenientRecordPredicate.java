package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Record;

import org.apache.log4j.Logger;

import com.mz.nhoz.dbf.comp.AsStringsObjectComparator;
import com.mz.nhoz.dbf.comp.ObjectComparator;
import com.mz.nhoz.dbf.comp.StandardObjectComparator;
import com.mz.nhoz.dbf.exception.RecordPredicateException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

/**
 * Intenta comparar valores varias veces de varias maneras antes de determinar
 * que son distintos.
 * 
 * Permite asignar varios comparadores a testear antes de descartar una
 * comparacion de valores.
 * 
 * @author martin.zaragoza
 *
 */
public class ValueEqualsLenientRecordPredicate extends ValueEqualsRecordPredicate {
	private Map<String, ObjectComparator> comparators = new HashMap<String, ObjectComparator>();

	private void __init() {
		addComparator(new StandardObjectComparator());
		addComparator(new AsStringsObjectComparator());
	}

	public ValueEqualsLenientRecordPredicate(Map<String, Object> keyValues) {
		super(keyValues);
		__init();
	}

	public ValueEqualsLenientRecordPredicate(String key, Object value) {
		super(key, value);
		__init();
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

	public ValueEqualsLenientRecordPredicate addComparator(ObjectComparator objectComparator) {
		comparators.put(objectComparator.toString(), objectComparator);
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

				boolean equals = __compare(recordValue, compareValue);

				if (equals == false) {
					return false;
				}
			}

//			Logger.getLogger(getClass()).info("registro " + recordContent.toString() + " encontrado!");

			return true;
		} catch (RecordUtilsException e) {
			throw new RecordPredicateException(e);
		}
	}// test

	private boolean __compare(Object recordValue, Object compareValue) {
		for (ObjectComparator objectComparator : comparators.values()) {
			if (objectComparator.compare(compareValue, recordValue) == true) {
				return true;
			}
		}

		return false;
	}
}
