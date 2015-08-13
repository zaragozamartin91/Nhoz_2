package com.mz.nhoz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.DbfReader;
import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfReaderException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import dummy.PersonRecord;

public class DbfReaderTest extends TestCase {
	private File dbfFile = new File("testFiles/DbfReaderTest.DBF");

	public void testGetRecord() throws RecordUtilsException, DbfReaderException, DbfManagerException {
		DbfReader dbfReader = new DbfReader(dbfFile);
		dbfReader.open();

		List<Record> records = new ArrayList<Record>();
		records.add(new RecordBuilder(new PersonRecord(1, "Martin", 250.23, 23).toMap()).build());
		records.add(new RecordBuilder(new PersonRecord(2, "Mateo", 123.12, 22).toMap()).build());
		records.add(new RecordBuilder(new PersonRecord(3, "Hector", 555.23, 55).toMap()).build());
		records.add(new RecordBuilder(new PersonRecord(4, "Sonia", 111.05, 50).toMap()).build());

		int i = 0;
		for (Record record : records) {
			assertTrue(RecordUtils.equals(record, dbfReader.getRecord(i)));
			i++;
		}

		dbfReader.close();
	}

	public void testGetRecordIndex() throws DbfManagerException, DbfReaderException {
		DbfReader dbfReader = new DbfReader(dbfFile);
		dbfReader.open();

		Record record = new RecordBuilder(new PersonRecord(2, "Mateo", 123.12, 22).toMap()).build();
		int expectedIndex = 1;
		int recordIndex = dbfReader.getRecordIndex(record);

		assertEquals(expectedIndex, recordIndex);

		Record missingRecord = new RecordBuilder(new PersonRecord(2, "MateX", 123.12, 22).toMap()).build();
		recordIndex = dbfReader.getRecordIndex(missingRecord);

		assertEquals(DbfReader.RECORD_NOT_FOUND, recordIndex);

		dbfReader.close();
	}
}
