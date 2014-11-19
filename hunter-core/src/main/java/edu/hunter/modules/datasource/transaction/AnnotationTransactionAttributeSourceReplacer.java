/**
 * @(#)AA.java 2013年12月17日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource.transaction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;

/**
 * @author Woo Cupid
 * @date 2013年12月17日
 * @version $Revision$
 */
@Component
public class AnnotationTransactionAttributeSourceReplacer implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

		// String[] names = factory.getBeanNamesForType(TransactionInterceptor.class);
		String[] names = factory.getBeanNamesForType(AnnotationTransactionAttributeSource.class);
		for (String name : names) {
			BeanDefinition bd = factory.getBeanDefinition(name);
			bd.setBeanClassName(MultiplyDatasourceAnnotationTransactionAttributeSource.class.getName());
		}
	}
}
