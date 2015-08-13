package tutorials.jexcel;

import java.io.File;
import java.io.IOException;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class JExcelAPIDemo_readXlsTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public JExcelAPIDemo_readXlsTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JExcelAPIDemo_readXlsTest.class);
	}
	
	public void testReadXls() throws BiffException, IOException, WriteException {
		Workbook workbook = Workbook.getWorkbook(new File("testFiles/output.xls"));
		Sheet sheet = workbook.getSheet(0);
		Cell cell1 = sheet.getCell(0, 2);
		System.out.println(cell1.getContents());
		Cell cell2 = sheet.getCell(3, 4);
		System.out.println(cell2.getContents());
		workbook.close();
	}
	
	public void testReadNewXlsx() throws BiffException, IOException, WriteException {
		Workbook workbook = Workbook.getWorkbook(new File("testFiles/newXlsx.xlsx"));
		Sheet sheet = workbook.getSheet(0);
//		Cell cell1 = sheet.getCell(0, 2);
//		System.out.println(cell1.getContents());
//		Cell cell2 = sheet.getCell(3, 4);
//		System.out.println(cell2.getContents());
		workbook.close();
	}
}