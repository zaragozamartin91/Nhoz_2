package com.mz.nhoz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.DbfWriter;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import junit.framework.TestCase;

public class DbfWriterTest extends TestCase {
	String pathname = "testFiles/PERSON.DBF";
	File dbaseFile = new File(pathname);

	public void testAddRecord() throws DbfWriterException, RecordUtilsException {
		// ID NAME SALARY AGE
		DbfWriter dbfWriter = new DbfWriter(dbaseFile);
		dbfWriter.open();

		int initialRecordCount = dbfWriter.getRecordCount();
		int recordCount = dbfWriter.getRecordCount();

		{
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("ID", 5);
			valueMap.put("NAME", "emanuel");
			valueMap.put("SALARY", 120.54);
			valueMap.put("AGE", 25);
			Record rec = dbfWriter.addRecord(valueMap);
			System.out.println( RecordUtils.toString(rec) );
			++recordCount;
		}
		{
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("ID", 9);
			valueMap.put("NAME", "roberto");
			valueMap.put("SALARY", 129.54);
			valueMap.put("AGE", 30);
			dbfWriter.addRecord(valueMap);
			++recordCount;
		}

		assertEquals(recordCount, dbfWriter.getRecordCount());

		dbfWriter.deleteLastRecord();
		dbfWriter.removeDeleted();
		dbfWriter.deleteLastRecord();
		dbfWriter.removeDeleted();

		assertEquals(initialRecordCount, dbfWriter.getRecordCount());

		dbfWriter.close();
	}// testAddRecord

	public void testUpdateRecord() {
	}

	public void testGetRecordCount() {
		try {
			DbfWriter dbfWriter = new DbfWriter(dbaseFile);

			dbfWriter.open();

			System.out.println("record count=" + dbfWriter.getRecordCount());

			dbfWriter.close();
		} catch (DbfWriterException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testDeleteRecord() {
	}

	public void testRemoveDeleted() {
	}

}
