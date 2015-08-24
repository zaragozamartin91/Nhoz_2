package com.mz.nhoz.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
	private Map map = new HashMap();

	public MapBuilder put(Object key, Object value) {
		map.put(key, value);
		return this;
	}

	public Map build() {
		return new HashMap(map);
	}
}
