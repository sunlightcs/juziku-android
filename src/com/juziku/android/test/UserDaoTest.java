package com.juziku.android.test;

import java.util.List;

import android.test.AndroidTestCase;

public class UserDaoTest extends AndroidTestCase {

	public void test() throws ClassNotFoundException {
		 
		 
	}

	public void testSave() {
		UserDao dao = new UserDao(getContext());
		User user = new User();
		user.setEmail("xxdsf@xx.com");
		user.setName("睡觉dsf");
		user.setSex(0);
		user.setBirthday(19870923);
		long id = dao.save(user);
		System.out.println("name:  " + id);
	}

	public void testUpdate() {
		UserDao dao = new UserDao(getContext());
		User user = new User();
		user.setId(1);
		user.setEmail("dada@xx.com");
		user.setName("睡觉");
		user.setSex(11);
		long row = dao.update(user);
		System.out.println("row:  " + row);
	}
	public void testDelete() {
		UserDao dao = new UserDao(getContext());
		long row = dao.delete("id", 1);
		System.out.println("row:  " + row);
	}
	
	public void testFind(){
		UserDao dao = new UserDao(getContext());
		List<User> userList = dao.queryForAll();
		for(User user : userList){
			System.out.println(user.getEmail() + "   " + user.getName());
		}
	}
}
