package junit.test;

import org.junit.Test;

import com.example.dao.impl.BaseDaoImpl;
import com.example.entity.User;
import com.example.utils.StringUtil;

public class MainTest {

	@Test
	public void testFormat() {
		String user = StringUtil.formatPropertyName("userName");
		System.out.println(user);
	}

	@Test
	public void testNull() {
		BaseDaoImpl<User> dao = new BaseDaoImpl<>();
		User user = new User(1, "阿亮", "123123", "liang@sina.com", null);
		dao.saveOrUpdate(user);
	}
	
	
}
