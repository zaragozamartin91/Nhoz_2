package com.mz.nhoz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.ValueSetRecordTransformer;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordTransformerException;
import com.mz.nhoz.dbf.util.DateUtils;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.DateUtilsException;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

public class ValueSetRecordTransformerTest extends TestCase {

	public void testTransform() throws RecordBuilderException, ParseException, RecordUtilsException, DateUtilsException, RecordTransformerException {
		Date date = DateUtils.getDate("dd/MM/yyyy", "21/03/1991");
		Record record = new RecordBuilder().put("name", "martin").put("age", 25).put("salary", 9874.6541).put("birth", date).build();

		{
			String nameKey = "name";
			String expectedName = "mateo";
			ValueSetRecordTransformer recordTransformer = new ValueSetRecordTransformer(nameKey, new String(expectedName));
			Record record__ = recordTransformer.transform(record);

			assertEquals(expectedName, RecordUtils.deserialize(record__).get(nameKey));
		}
		
		{
			String invalidKey = "asdasd";
			String expectedName = "mateo";
			ValueSetRecordTransformer recordTransformer = new ValueSetRecordTransformer(invalidKey, new String(expectedName));
			Record record__ = recordTransformer.transform(record);

			assertNotSame(expectedName, RecordUtils.deserialize(record__).get(invalidKey));
		}
	}//testTransform

}//ValueSetRecordTransformerTest
