/**
 * @(#)MultiplyDataSource.java 2013年11月13日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Woo Cupid
 * @date 2013年11月13日
 * @version $Revision$
 */
public class MultiplyDataSource extends AbstractRoutingDataSource {

	@SuppressWarnings("hiding")
	private static final Logger logger = LoggerFactory.getLogger(MultiplyDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		String key = DataSourceRouter.get();
		logger.debug("Use datasource with key [{}]", key);
		return key;
	}

}
