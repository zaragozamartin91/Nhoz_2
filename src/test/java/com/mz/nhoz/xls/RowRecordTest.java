package com.mz.nhoz.xls;

import java.io.File;

import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class RowRecordTest extends TestCase {

	public void testGet() throws ExcelReaderException {
		File excFile = new File("testFiles/RowRecordTest.xls");
		ExcelReader excelReader = new ExcelReader(excFile);
		
		TableHeader tableHeader = excelReader.getTableHeader();
		
	}

}
