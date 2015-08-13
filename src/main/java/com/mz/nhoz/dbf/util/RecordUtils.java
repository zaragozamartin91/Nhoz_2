package com.mz.nhoz.dbf.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Value;

import com.google.gson.Gson;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

/**
 * Utilidades para manejo de registros dbf.
 * 
 * @author martin.zaragoza
 *
 */
public class RecordUtils {
	/**
	 * Obtiene el mapa de valores que guarda un {@link Record}.
	 * 
	 * @param record
	 *            - registro a obtener mapa de valores.
	 * @return Mapa de valores del registro.
	 * @throws RecordUtilsException
	 */
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
	}// getValueMap

	/**
	 * Obtiene los datos del registro como un mapa clave->valor. Las claves son
	 * los encabezados del registro, los valores son objetos java.
	 * 
	 * @param record
	 *            - Registro a deserealizar.
	 * @return mapa clave->valor. Las claves son los encabezados del registro,
	 *         los valores son objetos java.
	 * @throws RecordUtilsException
	 */
	public static Map<String, Object> deserializeRecord(Record record) throws RecordUtilsException {
		Map<String, Value> valueMap = getValueMap(record);
		Set<String> keySet = valueMap.keySet();
		Map<String, Object> deserializedValues = new HashMap<String, Object>();

		for (String key : keySet) {
			Value value = valueMap.get(key);
			if (value == null) {
				deserializedValues.put(key, null);
				continue;
			}

			deserializedValues.put(key, record.getTypedValue(key));
		}

		return deserializedValues;
	}// deserializeRecord

	/**
	 * Obtiene una representacion como objeto String de un registro en formato
	 * Json.
	 * 
	 * @param record
	 *            - Registro.
	 * @return representacion como objeto String de un registro en formato Json.
	 * @throws RecordUtilsException
	 */
	public static String toString(Record record) throws RecordUtilsException {
		Map<String, Object> recordValues = deserializeRecord(record);
		Gson gson = new Gson();
		return gson.toJson(recordValues);
	}
}
