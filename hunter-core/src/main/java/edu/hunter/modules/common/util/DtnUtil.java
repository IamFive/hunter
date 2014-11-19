/**   
 * @Title: DtnUtil.java
 * @Package com.zfh.common.util
 * @date 2011-11-25 上午10:35:47
 * @version V1.0   
 */

package edu.hunter.modules.common.util;

import java.util.Random;

/**
 * @ClassName: DtnUtil
 * @Description: TODO(会员号生成类)
 * @author jfwu
 * @date 2011-11-25 上午10:35:47
 * 
 */
public class DtnUtil {
	public static long lastRand;

	// 生成DTN号
	public static Long GenDTN() {
		Random and = new Random(System.currentTimeMillis() + lastRand);
		long randN = and.nextLong() + lastRand;
		randN = Math.abs(randN) % 100000000;
		lastRand = randN;
		String s = "168" + randN + "00000000";
		return Long.valueOf(s.substring(0, 11));
	}

}
