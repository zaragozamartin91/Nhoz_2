package com.mz.nhoz;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mz.nhoz.exception.RecordBuilderException;
import com.mz.nhoz.exception.ValueParserException;

import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Value;

/**
 * Creador de Registros {@link nl.knaw.dans.common.dbflib.Record}.
 * 
 * Ejemplo de uso: <br/>
 * <code>Record r = new RecordBuilder().put("nombre","martin").put("edad",25).get();</code>
 * 
 * @author martin.zaragoza
 *
 */
public class RecordBuilder {
	private Map<String, Value> valueMap = new HashMap<String, Value>();
	private static final ValueParser VALUE_PARSER = new ValueParser();

	/**
	 * Crea un constructor de registros vacio.
	 */
	public RecordBuilder() {
		super();
	}

	/**
	 * Crea un constructor de registros a partir de un registro existente.
	 * 
	 * @param protoRecord
	 *            - Registro existente a partir del cual construir.
	 * @throws RecordBuilderException
	 */
	public RecordBuilder(Record protoRecord) throws RecordBuilderException {
		Class<? extends Record> recordClass = protoRecord.getClass();
		Field[] declaredFields = recordClass.getDeclaredFields();

		for (Field field : declaredFields) {
			String name = field.getName();
			if (name.contentEquals("valueMap")) {

				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				try {
					Object object = field.get(protoRecord);
					Map<String, Value> vm = (Map<String, Value>) object;
					valueMap = new HashMap<String, Value>(vm);
				} catch (Exception e) {
					throw new RecordBuilderException(e);
				}

				field.setAccessible(accessible);
				break;
			}
		}
	}// cons

	/**
	 * Agrega un valor al registro.
	 * 
	 * @param key
	 *            - Clave o id del valor.
	 * @param value
	 *            - Valor a asignar.
	 * @return this.
	 * @throws RecordBuilderException
	 */
	public RecordBuilder put(String key, Object value) throws RecordBuilderException {
		try {
			valueMap.put(key, VALUE_PARSER.parse(value));
		} catch (ValueParserException e) {
			throw new RecordBuilderException(e);
		}

		return this;
	}// put

	/**
	 * Obtiene un nuevo registro a partir de los valores asignados.
	 * 
	 * @return nuevo registro a partir de los valores asignados.
	 */
	public Record get() {
		Record record = new Record(valueMap);
		return record;
	}// get
}// RecordBuilder
