package com.mz.nhoz;

import nl.knaw.dans.common.dbflib.Record;

import com.mz.nhoz.dbf.ConditionalValueSetRecordTransformer;
import com.mz.nhoz.dbf.RecordBuilder;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.RecordBuilderException;
import com.mz.nhoz.dbf.exception.RecordTransformerException;
import com.mz.nhoz.dbf.util.RecordUtils;
import com.mz.nhoz.dbf.util.exception.RecordUtilsException;

import junit.framework.TestCase;

public class ConditionalValueSetRecordTransformerTest extends TestCase {

	public void testTransform() throws RecordBuilderException, RecordTransformerException, RecordUtilsException {
		Record record = new RecordBuilder().put("name", "mateo").put("salary", 12345.4567).put("age", 25).put("id", 1).build();

		String expName = "martin";
		ConditionalValueSetRecordTransformer transformer_1 = new ConditionalValueSetRecordTransformer("name", expName,
				new ValueEqualsRecordPredicate("id", 1));

		Record record__ = transformer_1.transform(record);

		assertFalse(RecordUtils.equals(record, record__));
		assertEquals(expName, RecordUtils.deserialize(record__).get("name"));
	}

}
