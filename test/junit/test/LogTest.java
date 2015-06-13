package junit.test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogTest {
	private Logger logger = Logger.getLogger(LogTest.class);
	@Test
	public void testLog() {
		logger.debug("11111111111111111111111111111111111111");
		logger.debug("22222222222222222222222222222222222222");
	}
}