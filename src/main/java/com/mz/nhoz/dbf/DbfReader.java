package com.mz.nhoz.dbf;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.DbfReaderException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

public class DbfReader extends DbfManager {
	public static final int RECORD_NOT_FOUND = -1;

	public DbfReader(File dbfFile) {
		super(dbfFile);
	}

	/**
	 * Retorna un registro a partir de un indice. NOTA: se tienen en cuenta
	 * registros borrados...
	 * 
	 * @param index
	 *            - Indice de registro a partir del cero.
	 * @return Registro.
	 * @throws DbfReaderException
	 */
	public Record getRecord(int index) throws DbfReaderException {
		try {
			return getTable().getRecordAt(index);
		} catch (Exception e) {
			throw new DbfReaderException(e);
		}
	}// getRecord

	/**
	 * Obtiene el indice de un registro. NOTA: se tienen en cuenta registros
	 * borrados...
	 * 
	 * @param record
	 *            - Registro a buscar.
	 * @return indice numerico del registro dentro de la tabla, -1 en caso de no encontrarlo.
	 * @throws DbfReaderException 
	 */
	public int getRecordIndex(Record record) throws DbfReaderException {
		Iterator<Record> recordIterator = getTable().recordIterator(true);
		int i = 0;
		
		try {
			while (recordIterator.hasNext()) {
				Record r = (Record) recordIterator.next();
				if(RecordUtils.equals(r, record)){
					return i;
				}
				i++;
			}
		} catch (RecordUtilsException e) {
			throw new DbfReaderException(e);
		}
		
		return RECORD_NOT_FOUND;
	}//getRecordIndex

	public List<Record> selectRecords() {
		return null;
	}

}
