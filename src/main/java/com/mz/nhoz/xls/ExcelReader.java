package com.mz.nhoz.xls;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mz.nhoz.xls.exception.ExcelReaderException;
import com.mz.nhoz.xls.util.CellDeserializer;

public class ExcelReader {
	private Workbook workbook;
	private Sheet sheet;
	private TableHeader tableHeader;

	/**
	 * Abre un documento de excel a partir de un archivo en la hoja 0.
	 * 
	 * @param excFile
	 *            - archivo xls o xlsX.
	 * @throws ExcelReaderException
	 */
	public ExcelReader(File excFile) throws ExcelReaderException {
		__reset(excFile, 0);
	}

	/**
	 * Abre un documento de excel a partir de un archivo y un indice de hoja.
	 * 
	 * @param excFile
	 *            - archivo xls o xlsX.
	 * @param sheetIndex
	 *            - indice de hoja.
	 * @throws ExcelReaderException
	 */
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

	/**
	 * Obtiene el encabezado de la tabla.
	 * 
	 * @return encabezado de la tabla.
	 * @throws ExcelReaderException
	 */
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
	}// __buildTableHeader
}// ExcelReader
