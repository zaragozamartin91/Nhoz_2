package misc;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class Log4jTest extends TestCase {
	public void testErrorLog() {
		Logger logger = Logger.getLogger(getClass());
		logger.error("some error");
	}
}
