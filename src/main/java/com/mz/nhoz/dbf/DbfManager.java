package com.mz.nhoz.dbf;

import java.io.File;
import java.io.IOException;

import nl.knaw.dans.common.dbflib.Table;

import com.mz.nhoz.dbf.exception.DbfManagerException;

public abstract class DbfManager {
	private Table table;

	public DbfManager(File dbfFile) {
		table = new Table(dbfFile);
	}

	public DbfManager(DbfManager dbfManager) {
		this.table = dbfManager.getTable();
	}

	protected Table getTable() {
		return table;
	}

	/**
	 * Abre una tabla.
	 * 
	 * @throws DbfManagerException
	 */
	public void open() throws DbfManagerException {
		try {
			getTable().open();
		} catch (Exception e) {
			throw new DbfManagerException(e);
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
		return getTable().getRecordCount();
	}

	/**
	 * Cierra la tabla para marcar el fin de su uso.
	 * 
	 * @throws DbfManagerException
	 */
	public void close() throws DbfManagerException {
		try {
			getTable().close();
		} catch (IOException e) {
			throw new DbfManagerException(e);
		}
	}
}// DbfManager
