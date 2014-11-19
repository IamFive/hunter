/**
 * @(#)TI.java 2013年12月18日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource.transaction;

import java.util.Properties;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.StringUtils;

import edu.hunter.modules.datasource.DataSourceRouter;
import edu.hunter.modules.datasource.transaction.MultiplyDatasourceTransitionalParser.MultiplyDatasourceTransactionAttribute;

/**
 * @author Woo Cupid
 * @date 2013年12月18日
 * @version $Revision$
 */
public class MultiplyDatasourceTransactionInterceptor extends TransactionInterceptor {

	private static final long serialVersionUID = -7313985938337353702L;

	public MultiplyDatasourceTransactionInterceptor() {
		super();
	}

	public MultiplyDatasourceTransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
		super(ptm, attributes);
	}

	public MultiplyDatasourceTransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
		super(ptm, tas);
	}

	@Override
	protected TransactionInfo createTransactionIfNecessary(PlatformTransactionManager tm, TransactionAttribute txAttr,
			final String joinpointIdentification) {

		boolean readOnly = txAttr.isReadOnly();
		DataSourceRouter.setReadOnly(readOnly);

		// woo modify here : 2014-04-29 17:33
		// if no MultiplyDatasourceTransactionAttribute is applied
		// we use default data source
		DataSourceRouter.clear();

		// Fucking spring use delegate but didn't provide public access method to original attribute
		if (txAttr instanceof MultiplyDatasourceTransactionAttribute) {
			MultiplyDatasourceTransactionAttribute mdta = (MultiplyDatasourceTransactionAttribute) txAttr;
			String dbName = mdta.getDbName();
			if (!StringUtils.isEmpty(dbName)) {
				DataSourceRouter.routeTo(dbName);
			}
		}

		return super.createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
	}

	@Override
	protected void cleanupTransactionInfo(TransactionInfo txInfo) {
		super.cleanupTransactionInfo(txInfo);
		DataSourceRouter.clear();
	}
}
