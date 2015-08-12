package com.mz.nhoz;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import nl.knaw.dans.common.dbflib.Database;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Version;

import com.mz.nhoz.exception.DbfWriterException;

/**
 * Editor de archivos Dbf.
 * 
 * @author martin.zaragoza
 *
 */
public class DbfWriter {
	private Database database;
	private Table table;

	public DbfWriter(File dbaseFile) {
		File parentFile = dbaseFile.getParentFile();
		String tableName = dbaseFile.getName();

		database = new Database(parentFile, Version.DBASE_3);
		table = database.getTable(tableName);
	}

	public DbfWriter open() throws DbfWriterException {
		try {
			table.open();
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
		return this;
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

			record = rb.get();

			table.addRecord(record);

			return record;
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
			Record protoRecord = table.getRecordAt(index);

			RecordBuilder rb = new RecordBuilder(protoRecord);

			Set<String> keySet = valueMap.keySet();

			for (String key : keySet) {
				Object value = valueMap.get(key);
				rb.put(key, value);
			}

			Record record = rb.get();

			table.updateRecordAt(index, record);

			return record;
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}

	/**
	 * Retorna la cantidad de registros en la tabla INCLUYENDO aquellos marcados
	 * como borrados.
	 * 
	 * @return cantidad de registros en la tabla INCLUYENDO aquellos marcados
	 *         como borrados.
	 */
	public int getRecordCount() {
		return table.getRecordCount();
	}

	/**
	 * Marca a un registro como "borrado".
	 * 
	 * @param index
	 *            - Indice de registro a borrar.
	 * @return registro marcado como borrado.
	 * @throws DbfWriterException
	 */
	public Record deleteRecord(int index) throws DbfWriterException {
		try {
			table.deleteRecordAt(index);
			return table.getRecordAt(index);
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}

	/**
	 * Elimina registros marcados como "borrados".
	 * 
	 * @return this.
	 * @throws DbfWriterException
	 */
	public DbfWriter removeDeleted() throws DbfWriterException {
		try {
			table.pack();
			return this;
		} catch (Exception e) {
			throw new DbfWriterException(e);
		}
	}

	/**
	 * Cierra la tabla para marcar el fin de su uso.
	 * 
	 * @throws DbfWriterException
	 */
	public void close() throws DbfWriterException {
		try {
			table.close();
		} catch (IOException e) {
			throw new DbfWriterException(e);
		}
	}
}// DbfWriter
