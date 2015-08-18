package misc;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MiscTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public MiscTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MiscTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testLinkedHashMap() {
		String[] keys = { "hola", "como", "estas", "todo", "bien" };
		LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();

		int length = keys.length;
		for (int i = 0; i < length; i++) {
			linkedHashMap.put(keys[i], new Integer(i));
		}

		assertTrue(Arrays.equals(keys, linkedHashMap.keySet().toArray()));
		Collection<Integer> values = linkedHashMap.values();
		int i = 0;
		for (Integer integer : values) {
			assertEquals(integer.intValue(), i++);
		}
	}

	public void testEquals() {
		Double d1 = new Double(4.25);
		Double d2 = new Double(4.25);

		assertEquals(d1, d2);
	}
	
	public void testCompareMapsUsingEquals(){
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		
		m1.put( new String("key_1") , new String("value_1"));
		m1.put( new String("key_2") , new Integer(2));
		m1.put( new String("key_3") , new Double(3.321));
		m1.put( new String("key_4") , new Float(4.654));
		
		
		m2.put( new String("key_2") , new Integer(2));
		m2.put( new String("key_4") , new Float(4.654));
		m2.put( new String("key_3") , new Double(3.321));
		m2.put( new String("key_1") , new String("value_1"));
		
		assertEquals(m1, m2);
	}
}
