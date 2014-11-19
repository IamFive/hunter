package edu.hunter.modules.common.util;

import java.util.Random;

/**
 * 
 * @ClassName: RandRomPwd
 * @Description: TODO()
 * @author fkl
 * @date 2013-04-12
 * 
 */
public class RandRomPwd {
	/**
	 * 
	 * @Title: getRandRomPwd
	 * @Description: TODO(随机获取6位密码)
	 * @param str
	 * @return
	 */
	public static String getRandRomPwd() {
		Random and = new Random(System.currentTimeMillis());
		long randN = and.nextLong();
		randN = Math.abs(randN);
		String randStr = String.valueOf(randN);
		int length = randStr.length() - 6;
		randStr = randStr.substring(length, randStr.length());
		return randStr;
	}

	public static void main(String[] args) {
		Random and = new Random(System.currentTimeMillis());
		long randN = and.nextLong();
		System.out.println(randN);
		randN = Math.abs(randN) % 1000000;
		String randStr = String.valueOf(randN);
		System.out.println(randStr);
	}
}
