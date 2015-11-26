/**
 * @(#)HibernateJpaVendorAdapter.java 2013年11月1日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource;

import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author Woo Cupid
 * @date 2013年11月1日
 * @version $Revision$
 */
@Deprecated
public class MultiplyDatasourceJpaVendorAdapter extends HibernateJpaVendorAdapter {

	private final HibernateJpaDialect dialect = new MultiplyDatasourceJpaDialect();

	@Override
	public HibernateJpaDialect getJpaDialect() {
		return this.dialect;
	}
}
