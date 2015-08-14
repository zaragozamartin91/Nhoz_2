package com.mz.nhoz.dbf;

import java.io.File;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.DbfWriterException;

/**
 * Editor de archivos Dbf.
 * 
 * @author martin.zaragoza
 *
 */
public class DbfWriter extends DbfManager {
	public DbfWriter(File dbfFile) {
		super(dbfFile);
	}

	public Record addRecord(Record record) throws DbfWriterException {
		try {
			getTable().addRecord(record);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
		return record;
	}

	public Record updateRecord(Record record, int index) throws DbfWriterException {
		try {
			getTable().updateRecordAt(index, record);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
		return record;
	}

	/**
	 * Agrega un registro al Dbf.
	 * 
	 * @param valueMap
	 *            - Mapa clave valor que representa al registro a agregar.
	 * @return Registro nuevo agregado.
	 * @throws DbfWriterException
	 */
	public Record addRecord(Map<String, Object> valueMap) throws DbfWriterException {
		try {
			Record record;
			RecordBuilder rb = new RecordBuilder();

			Set<String> keySet = valueMap.keySet();

			for (String key : keySet) {
				Object value = valueMap.get(key);
				rb.put(key, value);
			}

			record = rb.build();

			return this.addRecord(record);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}// addRecord

	/**
	 * Actualiza un registro.
	 * 
	 * @param valueMap
	 *            - valueMap - Mapa clave valor que representa al registro a
	 *            actualizar.
	 * @param index
	 *            - indice de registro.
	 * @return Registro actualizado.
	 * @throws DbfWriterException
	 */
	public Record updateRecord(Map<String, Object> valueMap, int index) throws DbfWriterException {
		try {
			Record protoRecord = getTable().getRecordAt(index);

			RecordBuilder rb = new RecordBuilder(protoRecord);

			Set<String> keySet = valueMap.keySet();

			for (String key : keySet) {
				Object value = valueMap.get(key);
				rb.put(key, value);
			}

			Record record = rb.build();

			return this.updateRecord(record, index);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}

	/**
	 * Marca a un registro como "borrado".
	 * 
	 * @param recordIndex
	 *            - Indice de registro a borrar inicia en 0.
	 * @return registro marcado como borrado.
	 * @throws DbfWriterException
	 */
	public Record deleteRecord(int recordIndex) throws DbfWriterException {
		try {
			getTable().deleteRecordAt(recordIndex);
			return getTable().getRecordAt(recordIndex);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}// deleteRecord

	/**
	 * Marca al ultimo registro como borrado.
	 * 
	 * @return Registro borrado.
	 * @throws DbfWriterException
	 */
	public Record deleteLastRecord() throws DbfWriterException {
		int index = getRecordCount() - 1;
		return this.deleteRecord(index);
	}

	/**
	 * Elimina registros marcados como "borrados".
	 * 
	 * @return this.
	 * @throws DbfWriterException
	 */
	public DbfWriter removeDeletedRecords() throws DbfWriterException {
		try {
			getTable().pack();
			return this;
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}
	
	public void updateRecords( RecordTransformer transformer ){
		
	}
}// DbfWriter
