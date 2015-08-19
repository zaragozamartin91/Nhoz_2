package misc;

import junit.framework.TestCase;

public class StringTest extends TestCase {
	public void testEqualsAndTrimWithDifferentStrings(){
		String s1 = new String("  hola como estas  		    ");
		String s2 = new String("hola como estas");
		
		assertEquals(s2, s1.trim());
	}
}
