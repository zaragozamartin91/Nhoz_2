package com.mz.nhoz.util;

import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.mz.nhoz.util.MapGen.KeyValuePair;

public class MapGenTest extends TestCase {
	MapGen mg = new MapGen();

	public void testCreate() {
		Map<Object, Object> map = mg.create(new KeyValuePair("key_1", "hello"), new KeyValuePair("key_2", "how"), new KeyValuePair("key_3", "are"));
		assertEquals(3, map.size());
	}

	public void testCreateAutogenKeys() {
		Map<Object, Object> map = mg.createAutogenKeys("hola", "como", "estas", "todo", "bien");
		Set<Object> keySet = map.keySet();

		int i = 0;

		for (Object key : keySet) {
			key.toString().contentEquals(MapGen.DEF_KEY_PREFIX + i);
			++i;
		}
	}
}
