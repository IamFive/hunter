/**
 * @(#)AA.java 2013年11月13日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import org.springframework.core.NamedThreadLocal;

/**
 * @author Woo Cupid
 * @date 2013年11月13日
 * @version $Revision$
 */
public abstract class DataSourceRouter {

	private static final ThreadLocal routeTo = new ThreadLocal();

	private static final ThreadLocal<Boolean> readOnly = new NamedThreadLocal<Boolean>(
			"Current transaction read-only status");

	public static void setReadOnly(boolean ro) {
		readOnly.set(ro ? Boolean.TRUE : null);
	}

	public static boolean isReadOnly() {
		return (readOnly.get() != null);
	}

	/**
	 * set current thread data-source
	 * 
	 * @param name the name of the data-source u want to route to.
	 */
	public static void routeTo(String name) {
		routeTo.set(name);
	}

	public static String get() {
		return (String) routeTo.get();
	}

	/**
	 * clear current route, then will use default data-source
	 */
	public static void clear() {
		routeTo.remove();
	}
}