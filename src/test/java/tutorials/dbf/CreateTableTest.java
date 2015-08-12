package tutorials.dbf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.knaw.dans.common.dbflib.Database;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Type;
import nl.knaw.dans.common.dbflib.Value;
import nl.knaw.dans.common.dbflib.Version;

/**
 * Unit test for simple App.
 */
public class CreateTableTest extends TestCase {
	public static final String DIR = "testFiles";
	public static final String TABLE_NAME = "PERSON.DBF";
	public static final String DBF_FILE_PATH = DIR + "/" + TABLE_NAME;
	
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CreateTableTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CreateTableTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 * @throws DbfLibException
	 */
	public void testCreate() throws IOException, DbfLibException {
		File file = new File(DBF_FILE_PATH);
		if (file.exists()) {
			file.delete();
		}

		Database database = new Database(new File(DIR), Version.DBASE_3);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("ID", Type.NUMBER, 3));
		fields.add(new Field("NAME", Type.CHARACTER, 25));
		fields.add(new Field("SALARY", Type.NUMBER, 10, 2));
		fields.add(new Field("AGE", Type.NUMBER, 6));
		int fieldCount = fields.size();
		int recordCount = 0;

		Table personTable = database.addTable(TABLE_NAME, fields);
		try {
			personTable.open(IfNonExistent.CREATE);
			personTable.addRecord(1, "Martin", new Double(250.23), 23);
			personTable.addRecord(2, "Mateo", new Double(123.12), 22);
			personTable.addRecord(3, "Hector", new Double(555.23), 55);
			personTable.addRecord(4, "Sonia", new Double(111.05), 50);

			recordCount = personTable.getRecordCount();
		} finally {
			personTable.close(); // don't forget to close it!
		}

		Table readTable = new Table(new File(DBF_FILE_PATH));
		readTable.open();
		
		
		List<Field> fields2 = readTable.getFields();

		assertEquals(recordCount, readTable.getRecordCount());
		assertEquals(fieldCount, fields2.size());
		readTable.close();
	}
	

}//CreateTableTest
