package com.mz.nhoz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mz.nhoz.dbf.comp.AsDoublesObjectComparator;
import com.mz.nhoz.dbf.comp.AsStringsIgnoreLeadingZeroesOjectComparator;
import com.mz.nhoz.dbf.comp.ObjectComparator;
import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.RowRecord;
import com.mz.nhoz.xls.RowRecordIterator;
import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class XlsRecordsAndComparatorsTest extends TestCase {
	public void testReadAndCompareXlsRecords() throws ExcelReaderException {
		ExcelReader excelReader = new ExcelReader(new File("testFiles/exel_38.xlsx"));

		final RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();

		List<ObjectComparator> comparators = new ArrayList<ObjectComparator>();
		comparators.add(new AsStringsIgnoreLeadingZeroesOjectComparator());
		comparators.add(new AsDoublesObjectComparator());

		RowRecord rowRecord = (RowRecord) rowRecordIterator.next();
		Object articulo = rowRecord.get("ARTICULO");

		for (ObjectComparator objectComparator : comparators) {
			if (objectComparator.compare("01-494", articulo)) {
				System.out.println("comparacion usando " + objectComparator + " paso!");
			}
		}
	}
}
