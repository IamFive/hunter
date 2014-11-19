/**
 * @(#)MyCon.java 2014年2月21日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

/**
 * @author Woo Cupid
 * @date 2014年2月21日
 * @version $Revision$
 */

@Component("conversionService")
public class MyConversionService extends DefaultFormattingConversionService {

	public MyConversionService() {
		super(); // no need for explicit super()?
		// addConverter(new MyConverter());
	}

}