package com.mz.nhoz;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mz.nhoz.dbf.DbfPredicateWriter;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.util.MapBuilder;
import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.RowRecord;
import com.mz.nhoz.xls.RowRecordIterator;
import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class AlterDbfFromXlsTest extends TestCase {
	public void testAlterDbfFromXls() throws ExcelReaderException, IOException, DbfManagerException, DbfWriterException {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("testFiles/testAlterDbfFromXls.xml");

		File listapre_dbf = (File) context.getBean("listapre_dbf");
		File testAlterDbfFromXls_dbf = (File) context.getBean("testAlterDbfFromXls_dbf");

		if (testAlterDbfFromXls_dbf.exists()) {
			testAlterDbfFromXls_dbf.delete();
		}

		FileUtils.copyFile(listapre_dbf, testAlterDbfFromXls_dbf);

		ExcelReader excelReader = (ExcelReader) context.getBean("excelReader");
		System.out.println("Archivo xls a usar: ");
		System.out.println(excelReader.toString());

		String codProv = "52";

		DbfPredicateWriter dbfPredicateWriter = (DbfPredicateWriter) context.getBean("dbfPredicateWriter");
		dbfPredicateWriter.open();

		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();
		while (rowRecordIterator.hasNext()) {
			RowRecord rowRecord = (RowRecord) rowRecordIterator.next();

			if (rowRecord.isEmpty()) {
				continue;
			}

			String articulo = (String) rowRecord.get("ARTICULO");
			ValueEqualsRecordPredicate predicate = new ValueEqualsRecordPredicate("CODIGOPROV", codProv).add("ARTICULO", articulo);
			dbfPredicateWriter.setPredicate(predicate);
			Double precioUni = (Double) rowRecord.get("PRECIOUNI");

			int updatedRecords = dbfPredicateWriter.updateRecords(new MapBuilder().put("PRECIOUNI", precioUni).build(), true);
			assertEquals(updatedRecords, 1);

			System.out.println(updatedRecords);
		}

		dbfPredicateWriter.close();
		context.close();
	}// testAlterDbfFromXls
}
