package com.mz.nhoz.dbf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordTransformerException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

public class ConditionalValueSetRecordTransformerTest extends TestCase {

//	public void testTransform() throws RecordBuilderException, RecordTransformerException, RecordUtilsException {
//		Record record = new RecordBuilder().put("name", "mateo").put("salary", 12345.4567).put("age", 25).put("id", 1).build();
//
//		String expName = "martin";
//		ConditionalValueSetRecordTransformer transformer_1 = new ConditionalValueSetRecordTransformer("name", expName,
//				new ValueEqualsRecordPredicate("id", 1));
//
//		Record record__ = transformer_1.transform(record);
//
//		assertFalse(RecordUtils.equals(record, record__));
//		assertEquals(expName, RecordUtils.deserialize(record__).get("name"));
//
//	}

	public void testChangeListapre() throws IOException, DbfManagerException, DbfWriterException {
		File LISTAPRE_DBF = new File("testFiles/LISTAPRE.DBF");
		File CHANGE_LISTAPRE_DBF = new File("testFiles/CHANGE_LISTAPRE.DBF");
		if (CHANGE_LISTAPRE_DBF.exists()) {
			CHANGE_LISTAPRE_DBF.delete();
		}

		FileUtils.copyFile(LISTAPRE_DBF, CHANGE_LISTAPRE_DBF);
		assertTrue(CHANGE_LISTAPRE_DBF.exists());
		
		RecordPredicate predicate = new ValueEqualsRecordPredicate().add("CODIGOPROV", new String("04")).add("ARTICULO", new String("0105"));
		ConditionalValueSetRecordTransformer transformer = (ConditionalValueSetRecordTransformer) new ConditionalValueSetRecordTransformer().add("DESCRIPCIO", "XXXXXXXXX");
		transformer.setPredicate(predicate);
		
		DbfWriter dbfWriter = new DbfWriter(CHANGE_LISTAPRE_DBF);
		dbfWriter.open();
		
		dbfWriter.updateRecords(transformer);
		
		dbfWriter.close();
	}
}
