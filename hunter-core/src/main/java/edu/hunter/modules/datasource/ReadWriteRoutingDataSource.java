/**
 * @(#)ReadWriteRoutingDataSource.java 2013年10月31日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * @author Woo Cupid
 * @date 2013年10月31日
 * @version $Revision$
 */
public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

	@SuppressWarnings("hiding")
	private static final Logger logger = LoggerFactory.getLogger(ReadWriteRoutingDataSource.class);

	public static final String PREFIX_READONLY = "R_";
	public static final String PREFIX_WRITABLE = "W_";

	private List<Object> readonlyKeys = Lists.newArrayList();
	private List<Object> writableKeys = Lists.newArrayList();

	private AtomicInteger rIndex = new AtomicInteger(0);
	private AtomicInteger wIndex = new AtomicInteger(0);

	/**
	 * Should not override this method indeed, should override afterPropertySet instead.
	 * But the the `targetDataSources` field of parent abstract class is private,
	 * And it doesn't provide a get method :(, so we handle this here.
	 */
	@Override
	protected Object resolveSpecifiedLookupKey(Object lookupKey) {
		logger.debug("Resolve datasource key [{}]", lookupKey);

		String key = lookupKey.toString();
		String dsName = null;
		if (StringUtils.startsWithIgnoreCase(key, PREFIX_READONLY)) {
			dsName = StringUtils.substringAfter(key, PREFIX_READONLY);
			readonlyKeys.add(dsName);
			logger.info("Add ds [{}] to readonly ds list.", dsName);
		} else if (StringUtils.startsWithIgnoreCase(key, PREFIX_WRITABLE)) {
			dsName = StringUtils.substringAfter(key, PREFIX_WRITABLE);
			writableKeys.add(dsName);
			logger.info("Add ds [{}] to writable ds list.", dsName);
		} else {
			logger.warn("Datasource key {} is not availble, will never be used!!!", lookupKey);
		}

		return dsName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {

		boolean isReadyOnly = DataSourceRouter.isReadOnly();

		// use default
		Object key = null;

		// we can implement a strategy schedule here :)
		// now we just choose ds by sequence
		if (isReadyOnly && !CollectionUtils.isEmpty(readonlyKeys)) {
			int index = rIndex.getAndAdd(1);
			key = readonlyKeys.get(index % readonlyKeys.size());
		}

		if (!isReadyOnly && !CollectionUtils.isEmpty(writableKeys)) {
			int index = wIndex.getAndAdd(1);
			key = writableKeys.get(index % readonlyKeys.size());
		}

		logger.debug("Use data-source : [{}]", key);
		return key;
	}

	/**
	 * @return the readonlyKeys
	 */
	public List<Object> getReadonlyKeys() {
		return readonlyKeys;
	}

	/**
	 * @return the writableKeys
	 */
	public List<Object> getWritableKeys() {
		return writableKeys;
	}

}
