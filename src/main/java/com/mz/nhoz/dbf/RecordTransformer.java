package com.mz.nhoz.dbf;

import com.mz.nhoz.dbf.exception.RecordTransformerException;

import nl.knaw.dans.common.dbflib.Record;

public interface RecordTransformer {
	/**
	 * Transforma un registro obteniendo un registro nuevo .
	 * 
	 * @param record
	 *            - Registro a partir del cual generar el nuevo.
	 * @return Registro nuevo transformado a partir del original.
	 * @throws RecordTransformerException
	 */
	public Record transform(Record record) throws RecordTransformerException;
}
