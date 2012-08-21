package com.juziku.android.test;

import android.test.AndroidTestCase;

public class YayaDaoTest extends AndroidTestCase{
	public void testSave(){
		Yaya yaya = new Yaya();
		yaya.setMoney("60000");
		
		YayaDao yayaDao = new YayaDao(getContext());
		yayaDao.save(yaya);
	}
	
	
	public void testFind(){
		YayaDao yayaDao = new YayaDao(getContext());
		int money = yayaDao.queryForInt("select money from " + yayaDao.getTableName() + " where money=?", new String[]{"60000"});
		int id = yayaDao.queryForInt("select money from " + yayaDao.getTableName() + " where money=?", new String[]{"60000"});
		System.out.println("money:  "+money);
		System.out.println("id:  "+id);
		
		
		UserDao dao = new UserDao(getContext());
		User user = new User();
		user.setEmail("xxx@xxx.com");
		user.setName("sss");
		user.setSex(1);
		
		dao.save(user);
		
		User u = dao.queryForObject("select * from " + dao.getTableName() + " limit 1", null);
		System.out.println("email:" + u.getEmail() + "   name:" + u.getName());
		
	}
}
