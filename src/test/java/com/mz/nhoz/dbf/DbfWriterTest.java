package com.mz.nhoz.dbf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.DbfWriter;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfReaderException;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import junit.framework.TestCase;

public class DbfWriterTest extends TestCase {
	String pathname = "testFiles/DbfWriterTest.DBF";
	File dbaseFile = new File(pathname);

	public void testAddRecord() throws DbfWriterException, RecordUtilsException, DbfManagerException {
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
			System.out.println(RecordUtils.toString(rec));
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
		dbfWriter.removeDeletedRecords();
		dbfWriter.deleteLastRecord();
		dbfWriter.removeDeletedRecords();

		assertEquals(initialRecordCount, dbfWriter.getRecordCount());

		dbfWriter.close();
	}// testAddRecord

	public void testUpdateRecord() {
	}

	public void testGetRecordCount() throws DbfManagerException {
		try {
			DbfWriter dbfWriter = new DbfWriter(dbaseFile);

			dbfWriter.open();

			System.out.println("record count=" + dbfWriter.getRecordCount());

			dbfWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testOpenSameTableTwice() throws DbfManagerException {
		DbfWriter dbfWriter_1 = new DbfWriter(dbaseFile);
		DbfWriter dbfWriter_2 = new DbfWriter(dbaseFile);

		dbfWriter_1.close();
		dbfWriter_2.close();
	}

	public void testUpdateRecordsUsingMapAndPredicate() throws DbfManagerException, IOException, DbfWriterException, DbfReaderException,
			RecordUtilsException {
		File testUpdateRecordsFile = new File("testFiles/testUpdateRecordsUsingMapAndPredicate.DBF");
		File LISTAPRE = new File("testFiles/LISTAPRE.DBF");

		if (testUpdateRecordsFile.exists()) {
			testUpdateRecordsFile.delete();
		}

		FileUtils.copyFile(LISTAPRE, testUpdateRecordsFile);

		DbfWriter dbfWriter = new DbfWriter(testUpdateRecordsFile);
		dbfWriter.open();

		// CODIGOPROV ARTICULO DESCRIPCIO
		String p_field = "CODIGOPROV";
		String p_fieldValue = "06";
		String v_field = "DESCRIPCIO";
		String v_fieldValue = "XXXXXXXX";

		RecordPredicate predicate = new ValueEqualsRecordPredicate(p_field, p_fieldValue);
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(v_field, v_fieldValue);
		dbfWriter.updateRecords(valueMap, predicate, false);

		dbfWriter.close();

		/*---------------------------------------------------------------------------------------*/

		DbfReader dbfReader = new DbfReader(testUpdateRecordsFile);
		dbfReader.open();

		List<Record> records = dbfReader.selectRecords(predicate);
		for (Record __record : records) {
			Map<String, Object> d_record = RecordUtils.deserialize(__record);
			assertEquals(v_fieldValue, d_record.get(v_field).toString().trim());
			assertEquals(p_fieldValue, d_record.get(p_field).toString().trim());
		}

		dbfReader.close();
	}//testUpdateRecordsUsingMapAndPredicate
}
