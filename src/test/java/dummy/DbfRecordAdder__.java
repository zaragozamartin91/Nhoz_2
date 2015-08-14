package dummy;

import java.io.File;
import java.util.HashMap;

import com.mz.nhoz.dbf.DbfWriter;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfWriterException;

/**
 * clase dummy para agregar registros a algun dbf...
 * 
 * @author martin.zaragoza
 *
 */
public class DbfRecordAdder__ {
	public static void main(String[] args) throws DbfManagerException, DbfWriterException {
		File dbfFile = new File("testFiles/DbfReaderTest.DBF");
		DbfWriter dbfWriter = new DbfWriter(dbfFile);
		dbfWriter.open();

		HashMap<String, Object> valueMap = new HashMap<String, Object>() {
			{
				put("ID", 6);
				put("NAME", "Mateo");
				put("SALARY", 100.99);
				put("AGE", 19);
			}
		};
		dbfWriter.addRecord(valueMap);

		dbfWriter.close();
	}
}
