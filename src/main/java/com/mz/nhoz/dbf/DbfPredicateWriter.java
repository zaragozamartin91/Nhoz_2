package com.mz.nhoz.dbf;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.util.RecordUtils;

public class DbfPredicateWriter extends DbfWriter {
	private RecordPredicate predicate;

	public DbfPredicateWriter(File dbfFile, RecordPredicate predicate) {
		super(dbfFile);
		this.predicate = predicate;
	}

	public RecordPredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(RecordPredicate predicate) {
		this.predicate = predicate;
	}

	/**
	 * Actualiza registros a partir de un predicado y un conjunto de valores a
	 * aplicar.
	 * 
	 * @param valueMap
	 *            - Valores a establecer a los registros.
	 * @param predicate
	 *            - Predicado que los registros a modificar deben cumplir.
	 * @param oneRecord
	 *            - True si se desea actualizar el primer registro que cumpla
	 *            con el predicado.
	 * @return Cantidad de registros modificados.
	 * @throws DbfWriterException
	 */
	public int updateRecords(Map<String, Object> valueMap, boolean oneRecord) throws DbfWriterException {
		try {
			Iterator<Record> recordIterator = getTable().recordIterator();
			int index = 0;
			int updatedCount = 0;

			while (recordIterator.hasNext()) {
				Record record = (Record) recordIterator.next();

				if (predicate.test(record)) {
					Record record__ = new RecordBuilder(record).putAll(valueMap).build();

					if (RecordUtils.equals(record, record__) == false) {
						this.updateRecord(record__, index);
						updatedCount++;
					}

					if (oneRecord) {
						return updatedCount;
					}
				}

				index++;
			}

			return updatedCount;
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}// updateRecords
}// DbfPredicateWriter
