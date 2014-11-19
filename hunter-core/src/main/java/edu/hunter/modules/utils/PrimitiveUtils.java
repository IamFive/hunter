/**
 * @(#)PrimitiveUtils.java 2014年1月21日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Woo Cupid
 * @date 2014年1月21日
 * @version $Revision$
 */
public class PrimitiveUtils {

	@SuppressWarnings("serial")
	private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {
		{
			add(Long.class);
			add(Integer.class);
			add(String.class);
			add(BigDecimal.class);
			add(BigInteger.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
		}
	};

	public final static boolean isPrimitive(Class<?> clazz) {
		if (clazz == null) {
			return true;
		}
		return clazz.isPrimitive() || PrimitiveClasses.contains(clazz);
	}

	public final static boolean isPrimitive(Object value) {
		if (value == null) {
			return true;
		}
		Class<? extends Object> clazz = value.getClass();
		return isPrimitive(clazz);
	}

	public static void main(String[] args) {
		int i = 1;
		System.out.println(isPrimitive(i));

		String a = "1";
		System.out.println(isPrimitive(a));

		System.out.println(isPrimitive(null));
	}
}
