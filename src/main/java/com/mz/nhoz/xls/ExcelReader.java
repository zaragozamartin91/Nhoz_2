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
	private int currRowIndex = 0;
	private Iterator<Row> rowIterator;

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

	/**
	 * Retorna un iterador de registros de filas.
	 * 
	 * @return iterador de registros de filas.
	 * @throws ExcelReaderException
	 */
	public RowRecordIterator rowRecordIterator() throws ExcelReaderException {
		try {
			Iterator<Row> __rowIterator = sheet.rowIterator();
			__rowIterator.next();
			return new RowRecordIterator(__rowIterator, getTableHeader());
		} catch (Exception e) {
			throw new ExcelReaderException(e);
		}
	}//rowRecordIterator

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

	private Row __nextRow() {
		if (this.rowIterator.hasNext()) {
			this.currRowIndex++;
			return this.rowIterator.next();
		}

		return null;
	}// __nextRow

	private void __resetRowIterator() {
		this.rowIterator = sheet.rowIterator();
		this.currRowIndex = 0;
	}// __resetRowIterator

	private void __checkResetRowIterator(int rowIndex) {
		if (rowIndex < this.currRowIndex) {
			__resetRowIterator();
		}
	}// __checkResetRowIterator

	private Row __getRow(int rowIndex) {
		__checkResetRowIterator(rowIndex);

		Row row = null;
		while (this.currRowIndex <= rowIndex) {

		}

		return null;
	}
}// ExcelReader
