package misc;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import junit.framework.TestCase;

public class NumberTest extends TestCase {
	public void testParseDoubleStringAsInteger() throws ParseException {
		Double d = 6.56;

		NumberFormat format = NumberFormat.getInstance(Locale.US);
		Number number = format.parse(d.toString());

		assertEquals(6, number.intValue());
	}
}
