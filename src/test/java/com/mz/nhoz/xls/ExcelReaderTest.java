package com.mz.nhoz.xls;

import java.io.File;

import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class ExcelReaderTest extends TestCase {
	private File xlsFile = new File("testFiles/ExcelReaderTest.xls");
	private File xlsXfile= new File("testFiles/ExcelReaderTest.xlsx");

	public void testGetTableHeaderXls() throws ExcelReaderException {
		__testGetTableHeader(xlsFile);
	}
	
	public void testGetTableHeaderXlsX() throws ExcelReaderException {
		__testGetTableHeader(xlsXfile);
	}
	
	private void __testGetTableHeader(File file) throws ExcelReaderException{
		// nombre apellido dni sueldo nacimiento
		ExcelReader excelReader = new ExcelReader(file);
		TableHeader expectedHeader = new TableHeader();
		
		expectedHeader.add("nombre", 0);
		expectedHeader.add("apellido", 1);
		expectedHeader.add("dni", 2);
		expectedHeader.add("sueldo", 3);
		expectedHeader.add("nacimiento", 4);
		
		TableHeader tableHeader = excelReader.getTableHeader();
		
		assertEquals(expectedHeader, tableHeader);
		expectedHeader.add("nacimiento", 5);
		assertNotSame(expectedHeader, tableHeader);
	}
}
