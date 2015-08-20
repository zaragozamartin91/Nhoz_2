package com.mz.nhoz.dbf;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.util.RecordUtils;

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

	public DbfWriter(DbfManager dbfManager) {
		super(dbfManager);
	}

	public Record addRecord(Record record) throws DbfWriterException {
		try {
			getTable().addRecord(record);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
		return record;
	}

	/**
	 * Actualiza un registro.
	 * 
	 * @param record
	 *            - Registro a actualizar.
	 * @param index
	 *            - Indice de registro a actualizar.
	 * @return record.
	 * @throws DbfWriterException
	 */
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
	public int updateRecords(Map<String, Object> valueMap, RecordPredicate predicate, boolean oneRecord) throws DbfWriterException {
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

	/**
	 * Actualiza todos los registros de una tabla a partir de un transformador
	 * de registros.
	 * 
	 * @param transformer
	 *            - Transformador de registros.
	 * @return cantidad de registros actualizados.
	 * @throws DbfWriterException
	 */
	public int updateRecords(RecordTransformer transformer) throws DbfWriterException {
		int updatedCount = 0;
		int index = 0;

		try {
			Iterator<Record> recordIterator = getTable().recordIterator();

			while (recordIterator.hasNext()) {
				Record record = (Record) recordIterator.next();
				Record record__ = transformer.transform(record);

				if (RecordUtils.equals(record, record__) == false) {
					this.updateRecord(record__, index);
					updatedCount++;
				}

				index++;
			}
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}

		return updatedCount;
	}
}// DbfWriter
