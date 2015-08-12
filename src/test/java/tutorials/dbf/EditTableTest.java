package tutorials.dbf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.knaw.dans.common.dbflib.Database;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Value;
import nl.knaw.dans.common.dbflib.Version;

/**
 * Unit test for simple App.
 */
public class EditTableTest extends TestCase {
	public static final String DIR = CreateTableTest.DIR;
	public static final String TABLE_NAME = CreateTableTest.TABLE_NAME;
	public static final String DBF_FILE_PATH = DIR + "/" + TABLE_NAME;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public EditTableTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(EditTableTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 * @throws DbfLibException
	 * @throws InterruptedException
	 */
	public void testEdit() throws IOException, DbfLibException, InterruptedException {
		if (new File(DBF_FILE_PATH).exists() == false) {
			new CreateTableTest("testCreate").testCreate();
		}

		Thread.sleep(1000);
		Table personTable = null;
		int recordEditIndex = 1;

		Double salaryValue = 999.99;
		Integer idValue = 25;
		String nameValue = "ROGELIO";
		Integer ageValue = 99;

		try {
			Database database = new Database(new File(DIR), Version.DBASE_3);

			personTable = database.getTable(TABLE_NAME);
			personTable.open();

			System.out.println("trabajando con " + DIR + "/" + TABLE_NAME);

			// ID NAME SALARY AGE
			Map<String, Value> valueMap = new HashMap<String, Value>();
			valueMap.put("ID", new NumberValue(idValue));
			valueMap.put("NAME", new StringValue(nameValue));
			valueMap.put("SALARY", new NumberValue(salaryValue));
			valueMap.put("AGE", new NumberValue(ageValue));

			Record record = new Record(valueMap);
			personTable.updateRecordAt(recordEditIndex, record);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				if (personTable != null)
					personTable.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // don't forget to close it!
		}

		Thread.sleep(1000);

		try {
			Database database = new Database(new File(DIR), Version.DBASE_3);

			personTable = database.getTable(TABLE_NAME);
			personTable.open();

			Record recordAt = personTable.getRecordAt(recordEditIndex);
			Object typedValue = null;

			typedValue = recordAt.getTypedValue("ID");
			assertEquals(typedValue, idValue);

			typedValue = recordAt.getTypedValue("SALARY");
			assertEquals(typedValue, salaryValue);

			typedValue = recordAt.getTypedValue("NAME");
			assertEquals(typedValue, nameValue);

			typedValue = recordAt.getTypedValue("AGE");
			assertEquals(typedValue, ageValue);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				if (personTable != null)
					personTable.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // don't forget to close it!
		}
	}
}// CreateTableTest
