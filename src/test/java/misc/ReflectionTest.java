package misc;

import junit.framework.TestCase;

public class ReflectionTest extends TestCase {
	public void testGetNumberSuperClassFromNumberSubclasses() {
		Integer i = new Integer(6);
		assertEquals(Number.class, i.getClass().getSuperclass());
		
		Double d= new Double(6.5);
		assertEquals(Number.class, d.getClass().getSuperclass());
		
		Float f= new Float(7.5);
		assertEquals(Number.class, f.getClass().getSuperclass());
	}
}
