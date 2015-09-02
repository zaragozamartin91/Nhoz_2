package misc;

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
		for (String s : in) {
			// System.out.println("[" + s.replaceFirst("^0+(?!$)", "") + "]");
			System.out.println(StringUtils.removeLeadingZeroes(s));
		}
	}
}
