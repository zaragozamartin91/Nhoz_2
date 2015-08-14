package com.mz.nhoz.dbf;

import com.mz.nhoz.dbf.exception.RecordPredicateException;

import nl.knaw.dans.common.dbflib.Record;

public interface RecordPredicate {
	/**
	 * Verifica una condicion sobre un registro retorna true si la misma se cumple.
	 * 
	 * @param record - Registro a testear.
	 * @return True si la condicion se cumple, false en caso contrario.
	 * @throws RecordPredicateException
	 */
	public boolean test(Record record) throws RecordPredicateException;
}
