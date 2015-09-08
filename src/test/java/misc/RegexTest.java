package misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mz.nhoz.util.StringUtils;

import junit.framework.TestCase;

public class RegexTest extends TestCase {
	public void testRemoveLeadingZeroes() {
		String[] in = { "01234", // "[1234]"
				"0001234a", // "[1234a]"
				"101234", // "[101234]"
				"000002829839", // "[2829839]"
				"0", // "[0]"
				"0000000", // "[0]"
				"0000009", // "[9]"
				"000000z", // "[z]"
				"000000.z", // "[.z]"
				"000094F5/7A" };

		String[] out = { "1234", // "[1234]"
				"1234a", // "[1234a]"
				"101234", // "[101234]"
				"2829839", // "[2829839]"
				"0", // "[0]"
				"0", // "[0]"
				"9", // "[9]"
				"z", // "[z]"
				".z", // "[.z]"
				"94F5/7A" };

		for (int i = 0; i < in.length; i++) {
			assertEquals(out[i], StringUtils.removeLeadingZeroes(in[i]));
		}
	}

	public void testReadTextPriceAsDouble() {
		/*
		 * {"PRECIOUNI":"$ 85.1000","CODIGO":"00030"}
		 * {"PRECIOUNI":"$ 100.5000","CODIGO":"00031"}
		 * {"PRECIOUNI":"$ 118.1000","CODIGO":"00032"}
		 * {"PRECIOUNI":"$ 150.8000","CODIGO":"00033"}
		 */

		{
			String s = "$ 150.8000";
			String ss = s.replaceAll("\\$ {0,}", "");
			assertEquals("150.8000", ss);
		}

		{
			String s = "$      150.8000";
			String ss = s.replaceAll("\\$ {0,}", "");
			assertEquals("150.8000", ss);
		}

		{
			String s = "150.8000";
			String ss = s.replaceAll("\\$ {0,}", "");
			assertEquals("150.8000", ss);
		}
	}

	public void testNumberRegex() {
		String doubleRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
		String doubleRegexNoE = "[-+]?[0-9]*\\.?[0-9]+";

		assertTrue("-123.456".matches(doubleRegex));
		assertTrue("-123".matches(doubleRegex));
		assertTrue("123.456".matches(doubleRegex));
		assertTrue("123".matches(doubleRegex));
		assertTrue("+123.456".matches(doubleRegex));
		assertTrue("+123".matches(doubleRegex));
		assertTrue("123.00000".matches(doubleRegex));
		assertTrue("123.0".matches(doubleRegex));

		assertFalse("123..12".matches(doubleRegex));
		assertFalse("--123.12".matches(doubleRegex));
		assertFalse("2207D".matches(doubleRegex));

		assertTrue("123.0E12".matches(doubleRegex));

		/* probando doubleRegexNoE-------------------------------------------- */

		assertTrue("-123.456".matches(doubleRegexNoE));
		assertTrue("-123".matches(doubleRegexNoE));
		assertTrue("123.456".matches(doubleRegexNoE));
		assertTrue("123".matches(doubleRegexNoE));
		assertTrue("+123.456".matches(doubleRegexNoE));
		assertTrue("+123".matches(doubleRegexNoE));
		assertTrue("123.00000".matches(doubleRegexNoE));
		assertTrue("123.0".matches(doubleRegexNoE));

		assertFalse("123..12".matches(doubleRegexNoE));
		assertFalse("--123.12".matches(doubleRegexNoE));
		assertFalse("2207D".matches(doubleRegexNoE));
		
		assertFalse("123.0E12".matches(doubleRegexNoE));

	}
}
