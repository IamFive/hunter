/**
 * @(#)TraLevelPreSetHibernateJpaDialect.java 2013年11月1日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

import edu.hunter.modules.datasource.transaction.MultiplyDatasourceTransactionInterceptorReplacer;

/**
 * TODO Need to reverse the configuration for tx
 * 
 * @author Woo Cupid
 * @date 2013年11月1日
 * @version $Revision$
 * @deprecated use {@link MultiplyDatasourceTransactionInterceptorReplacer} instead
 */
@Deprecated
public class MultiplyDatasourceJpaDialect extends HibernateJpaDialect {

	private static final long serialVersionUID = -810563347310854184L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.orm.jpa.vendor.HibernateJpaDialect#beginTransaction(javax.persistence.EntityManager,
	 * org.springframework.transaction.TransactionDefinition)
	 */
	@Override
	public Object beginTransaction(EntityManager entityManager, TransactionDefinition definition)
			throws PersistenceException, SQLException, TransactionException {
		// before start transaction, set the read-only property to ThreadLocal
		boolean readOnly = definition.isReadOnly();
		DataSourceRouter.setReadOnly(readOnly);

		// // Fucking spring use delegate but didn't provide public access method to original attribute
		// DelegatingTransactionDefinition fieldValue = (DelegatingTransactionDefinition)
		// edu.hunter.modules.utils.ReflectionUtils
		// .getFieldValue(definition, "targetDefinition");
		// Object fieldValue2 = edu.hunter.modules.utils.ReflectionUtils.getFieldValue(fieldValue, "targetAttribute");
		// if (fieldValue2 instanceof MultiplyDatasourceTransactionAttribute) {
		// MultiplyDatasourceTransactionAttribute mdta = (MultiplyDatasourceTransactionAttribute) fieldValue2;
		// String dbName = mdta.getDbName();
		// if (!StringUtils.isEmpty(dbName)) {
		// DataSourceRouter.routeTo(dbName);
		// } else {
		// DataSourceRouter.clear();
		// }
		// }

		return super.beginTransaction(entityManager, definition);
	}

}
