package junit.test;

import org.junit.Test;

import com.example.utils.IPUtils;

public class IPUtilsTest {
	@Test
	public void testIP() {
		IPUtils ipUtils = new IPUtils("218.241.197.98");
		System.out.println(ipUtils.getContent());
	}
}
