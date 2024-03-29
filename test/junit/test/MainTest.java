package junit.test;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.example.dao.BaseDao;
import com.example.dao.impl.BaseDaoImpl;
import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.Direction;
import com.example.utils.Page;
import com.example.utils.StringUtil;

public class MainTest {

	@Test
	public void testFormat() {
		String user = StringUtil.formatPropertyName("userName");
		System.out.println(user);
		System.out.println(StringUtils.uncapitalize("UserName"));
	}

	@Test
	public void testNull() {
		BaseDao<User, Integer> dao = new BaseDaoImpl<User, Integer>(){
		};
//		User user = new User(102, "阿亮2222222222", "123123", "liang@sina.com", null);
//		dao.save(user);
//		dao.update(user);
//		User dbuser = dao.getById(102);
//		System.out.println(dbuser);
		
		//		List<User> list = dao.getAll();
//		for(User dbuser : list) {
//			System.out.println(dbuser);
//		}
//		System.out.println(list.size());
//		User dbuser = dao.getByProperty("username", "thinkervsruler");
//		System.out.println(dbuser);
//		List<User> list = dao.getsByProperty("username", "lghuntfor");
//		for(User dbuser : list) {
//			System.out.println(dbuser);
//		}
//		LinkedHashMap<String, Direction> orders = new LinkedHashMap<>();
//		orders.put("id", Direction.DESC);
//		List<User> list = dao.getsByProperty("username", "lghuntfor", orders);
//		for(User dbuser : list) {
//			System.out.println(dbuser);
//		}
//		dao.updatePropertyById(102L, "username", "xxxx");
//		List<User> list = dao.like("ruler", "username");
//		for(User dbuser : list) {
//			System.out.println(dbuser);
//		}
//		String username = dao.getPropertyValueById(102L, "username");
//		System.out.println(username);
		
//		LinkedHashMap<String, Direction> orders = new LinkedHashMap<>();
//		orders.put("id", Direction.DESC);
//		Page<User> page = dao.getPage(1, 10, orders, "username", "lghuntfor");
//		for(User dbuser : page.getContent()) {
//			System.out.println(dbuser);
//		}
		
		UserServiceImpl userService = new UserServiceImpl();
		List<User> users= userService.getAll();
		System.out.println(users.size());
		
	}
	
}
