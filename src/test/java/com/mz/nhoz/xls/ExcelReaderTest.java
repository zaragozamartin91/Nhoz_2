package com.mz.nhoz.xls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mz.nhoz.util.MapBuilder;
import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class ExcelReaderTest extends TestCase {
	private File xlsFile = new File("testFiles/ExcelReaderTest.xls");
	private File xlsXfile = new File("testFiles/ExcelReaderTest.xlsx");

	public void testGetTableHeaderXls() throws ExcelReaderException {
		__testGetTableHeader(xlsFile);
	}

	public void testGetTableHeaderXlsX() throws ExcelReaderException {
		__testGetTableHeader(xlsXfile);
	}

	private void __testGetTableHeader(File file) throws ExcelReaderException {
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

	public void testGetTableHeaderWithBlanks() throws ExcelReaderException {
		System.out
				.println("testGetTableHeaderWithBlanks------------------------------------------------------------------------------");
		ExcelReader excelReader = new ExcelReader(new File("testFiles/testGetTableHeaderWithBlanks.xls"));

		TableHeader tableHeader = excelReader.getTableHeader();

		TableHeader expectedHeader = new TableHeader();
		expectedHeader.add("nombre", 0);
		expectedHeader.add("dni", 1);
		expectedHeader.add("nacimiento", 2);
		expectedHeader.add("apellido", 3);
		expectedHeader.add("sueldo", 4);

		assertEquals(expectedHeader, tableHeader);
	}

	public void testRowRecordIterator() throws ExcelReaderException {
		ExcelReader excelReader = new ExcelReader(new File("testFiles/testRowRecordIterator.xls"));
		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();

		// nombre apellido dni sueldo nacimiento
		List<Map<String, Object>> expectedRecords = new ArrayList<Map<String, Object>>();
		expectedRecords.add(new MapBuilder().put("nombre", "martin").put("apellido", "zaragoza")
				.put("dni", new Double(35657201)).put("sueldo", new Double(9000.12)).build());
		expectedRecords.add(new MapBuilder().put("nombre", "mateo").put("apellido", "zaragoza")
				.put("dni", new Double(36123456)).put("sueldo", new Double(9877.45)).build());
		expectedRecords.add(new MapBuilder().put("nombre", "sonia").put("apellido", "esposito")
				.put("dni", new Double(16528483)).put("sueldo", new Double(7987.12)).build());
		int index = 0;

		while (rowRecordIterator.hasNext()) {
			RowRecord rowRecord = (RowRecord) rowRecordIterator.next();

			Map<String, Object> __expectedRecord = expectedRecords.get(index);
			assertTrue(rowRecord.equalsMap(__expectedRecord));
			index++;

			System.out.println(rowRecord.toString());
		}
	}// testRowRecordIterator

	public void testReadXlsWithTextPrices() throws ExcelReaderException {
		System.out
				.println("testReadXlsWithTextPrices-----------------------------------------------------------------------------");
		ExcelReader excelReader = new ExcelReader(new File("testFiles/priceAsText.xlsx"));
		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();

		int i = 0;

		while (rowRecordIterator.hasNext() && (++i) < 5) {
			RowRecord rowRecord = (RowRecord) rowRecordIterator.next();
			System.out.println(rowRecord.toString());
		}
		
	}

	public void testOpenWrongTypeFile() {
		try {
			new ExcelReader(new File("someFile.asd"));
			fail();
		} catch (ExcelReaderException e) {
		}
	}
}
