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

	public void testParseUsingDifferentLocales() throws ParseException {
		{
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			Number number = format.parse("1,234");
			double d = number.doubleValue();

			assertEquals(new Double(1.234), d);
		}
		
		{
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number number = format.parse("1.234");
			double d = number.doubleValue();

			assertEquals(new Double(1234), d);
		}
		
		{
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number number = format.parse("1.234,12");
			double d = number.doubleValue();

			assertEquals(new Double(1234.12), d);
		}

		{
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse("1.234");
			double d = number.doubleValue();

			assertEquals(new Double(1.234), d);
		}
		
		{
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse("1,234");
			double d = number.doubleValue();

			assertEquals(new Double(1234), d);
		}
		
		{
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = format.parse("1,234.23");
			double d = number.doubleValue();

			assertEquals(new Double(1234.23), d);
		}
	}
}
