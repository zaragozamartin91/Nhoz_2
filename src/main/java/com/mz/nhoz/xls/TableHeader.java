package com.mz.nhoz.xls;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Representa el encabezado de una tabla de excel. Asocia nombres de columnas
 * con indices.
 * 
 * @author martin.zaragoza
 *
 */
public class TableHeader {
	private Map<String, Integer> map = new HashMap<String, Integer>();

	public void add(String label, Integer colIndex) {
		map.put(label, colIndex);
	}

	public Integer getColIndex(String label) {
		return map.get(label);
	}

	public String getLabel(Integer index) {
		Set<String> keySet = map.keySet();
		for (String label : keySet) {
			if (map.get(label).equals(index)) {
				return label;
			}
		}

		return null;
	}
}// TableHeader
