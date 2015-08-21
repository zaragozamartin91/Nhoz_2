package com.mz.nhoz.dbf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfReaderException;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

public class DbfPredicateWriterTest extends TestCase {
	String pathname = "testFiles/DbfWriterTest.DBF";
	File dbaseFile = new File(pathname);

	public void testUpdateRecordsUsingMapAndPredicate() throws DbfManagerException, IOException, DbfWriterException, DbfReaderException,
			RecordUtilsException {
		// CODIGOPROV ARTICULO DESCRIPCIO
		String p_field = "CODIGOPROV";
		String p_fieldValue = "06";
		String v_field = "DESCRIPCIO";
		String v_fieldValue = "XXXXXXXX";

		RecordPredicate predicate = new ValueEqualsRecordPredicate(p_field, p_fieldValue);

		File testUpdateRecordsFile = new File("testFiles/testUpdateRecordsUsingMapAndPredicate.DBF");
		File LISTAPRE = new File("testFiles/LISTAPRE.DBF");

		if (testUpdateRecordsFile.exists()) {
			testUpdateRecordsFile.delete();
		}

		FileUtils.copyFile(LISTAPRE, testUpdateRecordsFile);

		DbfPredicateWriter dbfWriter = new DbfPredicateWriter(testUpdateRecordsFile, predicate);
		dbfWriter.open();

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(v_field, v_fieldValue);
		dbfWriter.updateRecords(valueMap, false);

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
	}// testUpdateRecordsUsingMapAndPredicate

	public void testUpdateRecordsUsingMapAndPredicateSpring() throws DbfManagerException, IOException, DbfWriterException, DbfReaderException {
		File testUpdateRecordsFile = new File("testFiles/testUpdateRecordsUsingMapAndPredicateSpring.DBF");
		File LISTAPRE = new File("testFiles/LISTAPRE.DBF");

		if (testUpdateRecordsFile.exists()) {
			testUpdateRecordsFile.delete();
		}
		FileUtils.copyFile(LISTAPRE, testUpdateRecordsFile);

		
		String p_field = "CODIGOPROV";
		String p_fieldValue = "06";
		String v_field = "DESCRIPCIO";
		String v_fieldValue = "XXXXXXXX";

		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("testFiles/testUpdateRecordsUsingMapAndPredicateSpring.xml");

		DbfPredicateWriter dbfWriter = (DbfPredicateWriter) context.getBean("testUpdateRecordsUsingMapAndPredicateSpring_writer");
		dbfWriter.open();

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(v_field, v_fieldValue);
		dbfWriter.updateRecords(valueMap, false);
		dbfWriter.close();

		
		/*------------------------------------------------------------------------------------------*/
		
		
		
		context.close();
	}
}
