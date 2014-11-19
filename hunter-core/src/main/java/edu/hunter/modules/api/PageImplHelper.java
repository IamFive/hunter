/**
 * @(#)PageImplHelper.java 2014年7月15日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.google.common.collect.Maps;

/**
 * @author Woo Cupid
 * @date 2014年7月15日
 * @version $Revision$
 */
public class PageImplHelper {

	public static Map getPage(Page<?> page) {
		HashMap<String, Object> result = Maps.newHashMap();
		result.put("page", page.getNumber());
		result.put("size", page.getSize());
		result.put("total", page.getTotalPages());
		result.put("content", page.getContent());
		return result;
	}

}
