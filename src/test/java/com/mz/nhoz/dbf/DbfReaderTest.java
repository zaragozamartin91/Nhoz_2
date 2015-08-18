package com.mz.nhoz.dbf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import com.google.gson.Gson;
import com.mz.nhoz.dbf.DbfReader;
import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfReaderException;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import dummy.PersonRecord;

public class DbfReaderTest extends TestCase {
	private File dbfFile = new File("testFiles/DbfReaderTest.DBF");

	public void testGetRecord() throws RecordUtilsException, DbfReaderException, DbfManagerException, RecordBuilderException {
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
	}// testGetRecord

	public void testGetRecordIndex() throws DbfManagerException, DbfReaderException, RecordBuilderException {
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
	}// testGetRecordIndex

	public void testSelectRecords() throws DbfManagerException, DbfReaderException, RecordUtilsException {
		DbfReader dbfReader = new DbfReader(dbfFile);
		dbfReader.open();

		{
			ValueEqualsRecordPredicate predicate = new ValueEqualsRecordPredicate("AGE", new Integer(55));
			List<Record> selectRecords = dbfReader.selectRecords(predicate);

			assertFalse(selectRecords.isEmpty());
			assertEquals(2, selectRecords.size());
			
			
			PersonRecord person1 = new Gson().fromJson("{\"salary\":555.23,\"id\":3,\"name\":\"Hector\",\"age\":55}", PersonRecord.class);
			Map<String, Object> personMap1 = person1.toMap();
			PersonRecord person2 = new Gson().fromJson("{\"salary\":123.25,\"id\":5,\"name\":\"Raul\",\"age\":55}", PersonRecord.class);
			Map<String, Object> personMap2 = person2.toMap();
			
			for (Record record : selectRecords) {
				System.out.println(RecordUtils.toString(record));
				Map<String, Object> deserialize = RecordUtils.deserialize(record);
				assertTrue( deserialize.equals(personMap1) || deserialize.equals(personMap2) );
			}
		}
		
		{
			ValueEqualsRecordPredicate predicate = new ValueEqualsRecordPredicate("NAME", new String("Mateo"));
			List<Record> selectRecords = dbfReader.selectRecords(predicate);

			assertFalse(selectRecords.isEmpty());
			assertEquals(2, selectRecords.size());
			
			
			PersonRecord person1 = new Gson().fromJson("{\"salary\":123.12,\"id\":2,\"name\":\"Mateo\",\"age\":22}", PersonRecord.class);
			Map<String, Object> personMap1 = person1.toMap();
			PersonRecord person2 = new Gson().fromJson("{\"salary\":100.99,\"id\":6,\"name\":\"Mateo\",\"age\":19}", PersonRecord.class);
			Map<String, Object> personMap2 = person2.toMap();
			
			for (Record record : selectRecords) {
				System.out.println(RecordUtils.toString(record));
				Map<String, Object> deserialize = RecordUtils.deserialize(record);
				assertTrue( deserialize.equals(personMap1) || deserialize.equals(personMap2) );
			}
		}

		dbfReader.close();
	}//testSelectRecords

}
