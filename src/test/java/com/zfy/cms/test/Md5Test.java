package com.zfy.cms.test;

import org.junit.Test;

import Utils.Md5Utils;

public class Md5Test {

	@Test
	public void md5test(){
		String s = "wode mima";
		String m =Md5Utils.md5(s);
		System.out.println("密码是"+m);
	}
	
}
