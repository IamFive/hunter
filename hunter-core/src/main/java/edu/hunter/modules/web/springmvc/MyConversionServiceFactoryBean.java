/**
 * @(#)AA.java 2014年2月21日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.ConverterRegistry;

import edu.hunter.modules.conversion.impl.StringToDateConverter;

/**
 * @author Woo Cupid
 * @date 2014年2月21日
 * @version $Revision$
 */
public class MyConversionServiceFactoryBean extends ConversionServiceFactoryBean {

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		ConverterRegistry registry = (ConverterRegistry) this.getObject();
		registry.addConverter(new StringToDateConverter());
	}
}
