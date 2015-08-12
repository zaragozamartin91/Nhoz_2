package tutorials.dbf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;

/**
 * Unit test for simple App.
 */
public class ReadTableTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ReadTableTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ReadTableTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 * @throws DbfLibException
	 */
	public void testReadRecords() {
		try {
			System.out.println("testReadRecords-----------------------------------------------------------------------------------------");

			
			String pathname = CreateTableTest.DBF_FILE_PATH;
			if (new File(pathname).exists() == false) {
				System.err.println("No existe archivo " + pathname + " abortando prueba...");
				fail();
			}

			Table table = new Table(new File(pathname));
			table.open();

			Iterator<Record> recordIterator = table.recordIterator();
			while (recordIterator.hasNext()) {
				Record record = recordIterator.next();
				System.out.println(record.getStringValue("NAME"));
			}

			System.out.println(table.getRecordCount());

			table.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testReadFields() {
		try {
			System.out.println("testReadFields-----------------------------------------------------------------------------------------");
			
			Table table = new Table(new File(CreateTableTest.DBF_FILE_PATH));
			table.open();

			System.out.println("printing field names and types...");
			List<Field> fields = table.getFields();
			for (Field field : fields) {
				System.out.print(field.getName() + "::");
				System.out.println(field.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testReadListapre() {
		try {
			System.out.println("testReadListapre-----------------------------------------------------------------------------------------");
			
			String pathname = "testFiles/LISTAPRE.DBF";
			if (new File(pathname).exists() == false) {
				System.err.println("No existe archivo " + pathname + " abortando prueba...");
				fail();
			}

			Table table = new Table(new File(pathname));
			table.open();

			int recReadCount = 30;
			int i = 0;
			Iterator<Record> recordIterator = table.recordIterator();
			while (recordIterator.hasNext() && i++ < recReadCount) {
				Record record = recordIterator.next();
				System.out.println(record.getStringValue("ARTICULO"));
			}

			System.out.println(table.getRecordCount());

			table.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}// ReadTableTest
