/**
 * @(#)HibernateJpaVendorAdapter.java 2013年11月1日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author Woo Cupid
 * @date 2013年11月1日
 * @version $Revision$
 */
@Deprecated
public class MultiplyDatasourceJpaVendorAdapter extends HibernateJpaVendorAdapter {

	private final JpaDialect dialect = new MultiplyDatasourceJpaDialect();

	@Override
	public JpaDialect getJpaDialect() {
		return this.dialect;
	}
}
