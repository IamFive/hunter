/**
 * @(#)$.java 2012-1-10
 * Copyright 2000-2012 by iampurse@vip.qq.com. All rights reserved.
 */
package edu.hunter.modules.utils;

import java.util.*;

/**
 * @author qianbiao.wu
 * @date 2012-1-10
 * @version $Revision$
 */
public class CC {

	public static <T> Map<T, T> newHashMap(@SuppressWarnings("unchecked") T... keyValues) {
		assert (keyValues.length / 2) == 0;
		assert (keyValues.length / 2) == 0;
		Map<T, T> map = new HashMap<T, T>();
		for (int i = 0; i < keyValues.length; i += 2) {
			map.put(keyValues[i], keyValues[i + 1]);
		}
		return map;
	}

	/**
	 * @return
	 */
	public static ArrayList<Object> newArrayList(Object... objects) {
		ArrayList<Object> list = new ArrayList<Object>();
		for (Object object : objects) {
			list.add(object);
		}
		return list;
	}
}
