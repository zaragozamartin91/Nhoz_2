package com.mz.nhoz.xls;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mz.nhoz.xls.exception.ExcelReaderException;
import com.mz.nhoz.xls.util.CellDeserializer;
import com.mz.nhoz.xls.util.exception.CellDeserializerException;

public class ExcelReader {
	private Workbook workbook;
	private Sheet sheet;
	private TableHeader tableHeader;

	public ExcelReader(File excFile) throws ExcelReaderException {
		__reset(excFile, 0);
	}

	public ExcelReader(File excFile, int sheetIndex) throws ExcelReaderException {
		__reset(excFile, sheetIndex);
	}

	private void __reset(File excFile, int sheetIndex) throws ExcelReaderException {
		try {
			ExcelDocType excelDocType = new ExcelDocType(excFile);
			FileInputStream fis = new FileInputStream(excFile);

			if (excelDocType.isXls()) {
				workbook = new HSSFWorkbook(fis);
			} else {
				workbook = new XSSFWorkbook(fis);
			}

			sheet = workbook.getSheetAt(sheetIndex);
		} catch (Exception e) {
			throw new ExcelReaderException(e);
		}
	}// __reset

	public TableHeader getTableHeader() throws ExcelReaderException {
		if (tableHeader == null) {
			__buildTableHeader();
		}

		return tableHeader;
	}// getTableHeader

	private void __buildTableHeader() throws ExcelReaderException {
		try {
			CellDeserializer cd = new CellDeserializer();
			tableHeader = new TableHeader();

			Iterator<Row> rowIterator = sheet.iterator();
			Row firstRow = rowIterator.next();
			Iterator<Cell> cellIterator = firstRow.cellIterator();

			int index = 0;
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				tableHeader.add((String) cd.deserialize(cell), index);
				index++;
			}
		} catch (Exception e) {
			throw new ExcelReaderException(e);
		}
	}//__buildTableHeader
}// ExcelReader
