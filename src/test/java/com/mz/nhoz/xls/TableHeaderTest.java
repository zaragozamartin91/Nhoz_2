package com.mz.nhoz.xls;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

public class TableHeaderTest extends TestCase {
	public void testGetColIndex() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Random r = new Random();
		TableHeader th = new TableHeader();

		for (int i = 0; i < 20; i++) {
			String label = "key_" + i;
			int index = r.nextInt(1000);
			map.put(label, index);
			th.add(label, index);
		}

		Set<String> keySet = map.keySet();
		for (String label : keySet) {
			assertEquals(map.get(label), th.getColIndex(label));
		}
	}

	public void testGetLabel() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		TableHeader th = new TableHeader();

		for (int i = 0; i < 20; i++) {
			String label = "key_" + i;
			int index = i;
			map.put(label, index);
			th.add(label, index);
		}
		
		Set<String> keySet = map.keySet();
		for (String label : keySet) {
			Integer index = map.get(label);
			assertEquals(label, th.getLabel(index));
		}
	}
	
	public void testEquals(){
		TableHeader th_1 = new TableHeader();
		TableHeader th_2 = new TableHeader();
		Random r = new Random();

		
		for (int i = 0; i < 20; i++) {
			String label = "key_" + i;
			int index = r.nextInt(1000);
			th_1.add( new String(label) , new Integer(index) );
			th_2.add( new String(label) , new Integer(index) );
		}
		
		assertEquals(th_1, th_2);
		th_2.add("label extra", 98797);
		assertNotSame(th_1, th_2);
	}
}//TableHeaderTest
