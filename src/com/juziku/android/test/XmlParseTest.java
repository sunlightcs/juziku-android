package com.juziku.android.test;

import android.test.AndroidTestCase;

public class XmlParseTest extends AndroidTestCase{
	public void test(){
		System.out.println("test...."+Test.class.getFields()[0]);
		System.out.println(Test.class.getFields()[0].getType());
		System.out.println(Test.class.getDeclaredFields()[0].getType().getSuperclass());
	}
	
	class Test extends XmlParseTest{
		private int aa;

		public int getAa() {
			return aa;
		}

		public void setAa(int aa) {
			this.aa = aa;
		}
		
	}
	

}
