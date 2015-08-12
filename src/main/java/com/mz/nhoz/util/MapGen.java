package com.mz.nhoz.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGen {
	public final static String DEF_KEY_PREFIX = "key_";

	public Map<Object, Object> create(KeyValuePair... pairs) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		for (KeyValuePair pair : pairs) {
			map.put(pair.key, pair.value);
		}

		return map;
	}

	public Map<Object, Object> createAutogenKeys(Object... values) {
		List<KeyValuePair> list = new ArrayList<MapGen.KeyValuePair>();

		int i = 0;
		for (Object value : values) {
			list.add(new KeyValuePair(DEF_KEY_PREFIX + i, value));
			++i;
		}

		KeyValuePair[] v = new KeyValuePair[1];
		return create(list.toArray(v));
	}

	public static class KeyValuePair {
		private Object key;
		private Object value;

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		public KeyValuePair(Object key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
}
