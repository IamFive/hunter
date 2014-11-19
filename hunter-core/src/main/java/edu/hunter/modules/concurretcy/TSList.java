/**
 * @(#)TSList.java 2012-1-20
 * Copyright 2000-2012 by iampurse@vip.qq.com. All rights reserved.
 */
package edu.hunter.modules.concurretcy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * thread safe list
 * 
 * @author Five
 * @date 2012-1-20
 * @version $Revision$
 */
public class TSList<T> extends ArrayList<T> {

	/**
     * 
     */
	private static final long serialVersionUID = 8347355519110276406L;

	/**
     * 
     */
	public TSList() {
		super();
		Collections.synchronizedList(this);
	}

	/**
	 * @param c
	 */
	public TSList(Collection<? extends T> c) {
		super(c);
		Collections.synchronizedList(this);
	}

	/**
	 * @param initialCapacity
	 */
	public TSList(int initialCapacity) {
		super(initialCapacity);
		Collections.synchronizedList(this);
	}

	synchronized public ArrayList<T> drainTo() {
		ArrayList<T> list = new ArrayList<T>();
		list.addAll(this);
		this.clear();
		return list;
	}

}
