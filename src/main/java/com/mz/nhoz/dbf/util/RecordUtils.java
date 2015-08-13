package com.mz.nhoz.dbf.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.util.exception.RecordUtilsException;

import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Value;

public class RecordUtils {
	public static Map<String, Value> getValueMap(Record record) throws RecordUtilsException {
		Class<? extends Record> recordClass = record.getClass();
		Field[] declaredFields = recordClass.getDeclaredFields();
		Map<String, Value> vm = null;

		for (Field field : declaredFields) {
			String name = field.getName();
			if (name.contentEquals("valueMap")) {

				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				try {
					Object object = field.get(record);
					vm = (Map<String, Value>) object;
				} catch (Exception e) {
					throw new RecordUtilsException(e);
				}

				field.setAccessible(accessible);
				break;
			}
		}

		return vm;
	}
}//getValueMap
