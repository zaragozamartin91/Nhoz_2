package com.mz.nhoz;

import java.io.File;
import java.io.IOException;

import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.Database;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Version;

public class DbfWriter {
	private Database database;
	private Table table;
	
	public DbfWriter(File dbaseFile) {
		File parentFile = dbaseFile.getParentFile();
		String tableName = dbaseFile.getName();

		database = new Database(parentFile, Version.DBASE_3);
		table = database.getTable(tableName);
	}

	public DbfWriter open() throws CorruptedTableException, IOException{
		table.open();
		return this;
	}
	
	
}//DbfWriter
