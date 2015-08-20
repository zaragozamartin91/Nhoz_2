package com.mz.nhoz.xls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.gson.Gson;
import com.mz.nhoz.xls.exception.RowRecordException;
import com.mz.nhoz.xls.util.CellDeserializer;
import com.mz.nhoz.xls.util.exception.CellDeserializerException;

/**
 * Representa un registro de fila de Excel.
 * 
 * @author martin.zaragoza
 *
 */
public class RowRecord {
	public static final CellDeserializer CELL_DESERIALIZER = new CellDeserializer();

	private Map<String, Object> valueMap = new HashMap<String, Object>();

	public RowRecord(Row row, TableHeader header) throws RowRecordException {
		super();

		try {
			__build(row, header);
		} catch (Exception e) {
			throw new RowRecordException(e);
		}
	}// cons

	private void __build(Row row, TableHeader header) throws CellDeserializerException {
		Iterator<Cell> cellIterator = row.cellIterator();

		int index = 0;

		while (cellIterator.hasNext()) {
			Cell cell = (Cell) cellIterator.next();
			Object deserializedCellValue = CELL_DESERIALIZER.deserialize(cell);
			String cellLabel = header.getLabel(index);

			valueMap.put(cellLabel, deserializedCellValue);
			++index;
		}
	}// __build

	public Set<String> keys() {
		return this.valueMap.keySet();
	}

	public Collection<Object> values() {
		return this.valueMap.values();
	}

	public Object get(String cellLabel) {
		return this.valueMap.get(cellLabel);
	}// get

	public String toString() {
		return new Gson().toJson(this.valueMap);
	}
}// RowRecord
