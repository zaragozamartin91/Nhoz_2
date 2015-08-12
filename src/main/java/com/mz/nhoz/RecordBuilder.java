package com.mz.nhoz;

import java.util.HashMap;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Value;

import com.mz.nhoz.exception.RecordBuilderException;
import com.mz.nhoz.exception.ValueParserException;
import com.mz.nhoz.util.RecordUtils;
import com.mz.nhoz.util.exception.RecordUtilsException;

/**
 * Creador de Registros de tipo {@link nl.knaw.dans.common.dbflib.Record}.
 * 
 * Ejemplo de uso: <br/>
 * <code>Record r = new RecordBuilder().put("nombre","martin").put("edad",25).get();</code>
 * 
 * @author martin.zaragoza
 *
 */
public class RecordBuilder {
	private Map<String, Value> valueMap = new HashMap<String, Value>();
	private static final ValueSerializer VALUE_PARSER = new ValueSerializer();

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
		try {
			Map<String, Value> valueMap__ = RecordUtils.getValueMap(protoRecord);
			valueMap = new HashMap<String, Value>(valueMap__ );
		} catch (RecordUtilsException e) {
			throw new RecordBuilderException(e);
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
