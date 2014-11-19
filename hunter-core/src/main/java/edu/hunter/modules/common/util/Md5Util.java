/**   
 * @Title: Md5Util.java
 * @Package com.zfh.common.util
 * @date 2011-12-6 下午12:53:35
 * @version V1.0   
 */

package edu.hunter.modules.common.util;

import java.security.MessageDigest;

/**
 * @ClassName: Md5Util
 * @Description: TODO(md5加密类)
 * @author fkl
 * @date 2011-12-6 下午12:53:35
 * 
 */
public class Md5Util {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * cipher password
	 * 
	 * @param inputString
	 * @return
	 */
	public static String generatePassword(String inputString) {
		return encode(inputString);
	}

	/**
	 * validate password
	 * 
	 * @param password
	 * @param inputString
	 * @return
	 */
	public static boolean validatePassword(String password, String inputString) {
		if (password.equals(encode(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * encode
	 * 
	 * @param originString
	 * @return
	 */
	public static String encode(String originString) {
		if (originString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes("UTF-8"));
				String resultString = byteArrayToHexString(results);
				return resultString;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * change the Byte[] to hex string
	 * 
	 * @param b
	 * @return
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (byte element : b) {
			resultSb.append(byteToHexString(element));
		}
		return resultSb.toString();
	}

	/**
	 * change a byte to hex string
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

}
