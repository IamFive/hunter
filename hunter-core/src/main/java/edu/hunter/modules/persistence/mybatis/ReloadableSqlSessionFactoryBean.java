/**
 * @(#)ReloadableSqlSessionFactoryBean.java 2014年10月24日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.persistence.mybatis;

import java.util.*;

import org.apache.ibatis.session.*;
import org.mybatis.spring.*;
import org.slf4j.*;
import org.springframework.core.io.*;

/**
 * 
 * not work, spring wont only call getObject once when initial beans.
 * 
 * @author Woo Cupid
 * @date 2014年10月24日
 * @version $Revision$
 */
@Deprecated
public class ReloadableSqlSessionFactoryBean extends SqlSessionFactoryBean {

	private static final Logger logger = LoggerFactory.getLogger(ReloadableSqlSessionFactoryBean.class);

	protected Date loadOn = new Date();
	private Resource[] mapperLocations;

	@Override
	public SqlSessionFactory getObject() throws Exception {
		long lastModified = 0L;
		for (Resource resource : mapperLocations) {
			if (lastModified < resource.getFile().getAbsoluteFile().lastModified()) {
				lastModified = resource.getFile().getAbsoluteFile().lastModified();
			}
		}

		if (lastModified > loadOn.getTime()) {
			super.afterPropertiesSet();
			loadOn = new Date();
			logger.info("reload mybatis sql mapper xmls.");
		}

		return super.getObject();
	}

	/**
	 * super implemention doesn't provide get method..
	 */
	@Override
	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
		super.setMapperLocations(mapperLocations);
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
