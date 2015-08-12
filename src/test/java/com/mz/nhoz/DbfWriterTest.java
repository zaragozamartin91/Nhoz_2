package com.mz.nhoz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mz.nhoz.exception.DbfWriterException;

import junit.framework.TestCase;

public class DbfWriterTest extends TestCase {
	String pathname = "testFiles/PERSON.DBF";
	File dbaseFile = new File(pathname);

	public void testAddRecord() throws DbfWriterException {
		// ID NAME SALARY AGE
		DbfWriter dbfWriter = new DbfWriter(dbaseFile);
		dbfWriter.open();

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("ID", 5);
		valueMap.put("NAME", "martin");
		valueMap.put("SALARY", 120.54);
		valueMap.put("AGE", 25);
		dbfWriter.addRecord(valueMap);

		int recordCount = dbfWriter.getRecordCount();
		assertEquals(5, recordCount);
		
		dbfWriter.deleteRecord(recordCount - 1);
		dbfWriter.removeDeleted();

		dbfWriter.close();
	}

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
