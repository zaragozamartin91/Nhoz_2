package com.mz.nhoz.xls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	/**
	 * Obtiene el indice de columna a partir de un nombre de encabezado.
	 * 
	 * @param label
	 *            - nombre de columna encabezado.
	 * @return indice de columna.
	 */
	public Integer getColIndex(String label) {
		return map.get(label);
	}

	/**
	 * Obtiene un label de encabezado a partir de un indice de columna.
	 * 
	 * @param index
	 *            - Indice de encabezado a buscar.
	 * @return
	 */
	public String getLabel(Integer index) {
		if (map.isEmpty()) {
			return null;
		}

		Set<String> keySet = map.keySet();
		for (String label : keySet) {
			if (map.get(label).equals(index)) {
				return label;
			}
		}

		return null;
	}

	/**
	 * Obtiene todos los labels de encabezado.
	 * 
	 * @return lista de labels de encabezado.
	 */
	public List<String> getLabels() {
		List<String> list = new ArrayList<String>();
		list.addAll(map.keySet());
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableHeader other = (TableHeader) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}
}// TableHeader
