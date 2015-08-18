package com.mz.nhoz.xls.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import com.mz.nhoz.xls.util.exception.CellDeserializerException;

public class CellDeserializer {
	private FormulaEvaluator formulaEvaluator;
	public static final String BLANK = "";

	public CellDeserializer() {
		super();
	}

	public CellDeserializer(FormulaEvaluator formulaEvaluator) {
		super();
		this.formulaEvaluator = formulaEvaluator;
	}

	public Object deserialize(Cell cell) throws CellDeserializerException {
		try {
			int cellType = cell.getCellType();
			String cellPos = cell.getRowIndex() + ":" + cell.getColumnIndex();

			switch (cellType) {
				case Cell.CELL_TYPE_NUMERIC:
					return cell.getNumericCellValue();
				case Cell.CELL_TYPE_STRING:
					return cell.getStringCellValue();
				case Cell.CELL_TYPE_BOOLEAN:
					return cell.getBooleanCellValue();
				case Cell.CELL_TYPE_BLANK:
					return BLANK;
				case Cell.CELL_TYPE_FORMULA:
					if (formulaEvaluator == null) {
						throw new CellDeserializerException("No se dispone de un evaluador de formulas para deseralizar la celda " + cellPos);
					}
					return formulaEvaluator.evaluate(cell).getNumberValue();
			}

			throw new CellDeserializerException("Imposible deserealizar celda " + cellPos);
		} catch (Exception e) {
			throw new CellDeserializerException(e);
		}
	}// deserialize

	public boolean isBlank(Cell cell) throws CellDeserializerException {
		try {
			return cell.getCellType() == Cell.CELL_TYPE_BLANK;
		} catch (Exception e) {
			throw new CellDeserializerException(e);
		}
	}// isBlank
}// CellDeserializer
