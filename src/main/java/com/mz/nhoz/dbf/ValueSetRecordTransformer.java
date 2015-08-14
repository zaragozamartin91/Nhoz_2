package com.mz.nhoz.dbf;

import java.util.HashMap;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.RecordTransformerException;

/**
 * Realiza transformacion a un registro modificando uno o mas de sus valores
 * obteniendo un registro distinto al original.
 * 
 * @author martin.zaragoza
 *
 */
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

	/**
	 * Agrega un valor a modificar.
	 * 
	 * @param key
	 *            - Nombre del valor a modificar.
	 * @param value
	 *            - Valor efectivo a setear.
	 * @return this.
	 */
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
	}// transform
}// ValueSetRecordTransformer
