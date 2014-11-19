/**
 * @(#)AA.java 2013年12月17日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource.transaction;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.util.ClassUtils;

/**
 * @author Woo Cupid
 * @date 2013年12月17日
 * @version $Revision$
 */
public class MultiplyDatasourceAnnotationTransactionAttributeSource extends AnnotationTransactionAttributeSource {

	private static final long serialVersionUID = 1L;

	private static final boolean ejb3Present = ClassUtils.isPresent("javax.ejb.TransactionAttribute",
			AnnotationTransactionAttributeSource.class.getClassLoader());

	private static final LinkedHashSet<TransactionAnnotationParser> parseres = new LinkedHashSet<TransactionAnnotationParser>();
	static {
		parseres.add(new SpringTransactionAnnotationParser());
		parseres.add(new MultiplyDatasourceTransitionalParser());
		if (ejb3Present) {
			parseres.add(new Ejb3TransactionAnnotationParser());
		}
	}

	public MultiplyDatasourceAnnotationTransactionAttributeSource() {
		super(parseres);
	}

	/**
	 * @param publicMethodsOnly
	 */
	public MultiplyDatasourceAnnotationTransactionAttributeSource(boolean publicMethodsOnly) {
		super(publicMethodsOnly);
	}

	/**
	 * @param annotationParsers
	 */
	public MultiplyDatasourceAnnotationTransactionAttributeSource(Set<TransactionAnnotationParser> annotationParsers) {
		super(annotationParsers);
	}

	/**
	 * @param annotationParsers
	 */
	public MultiplyDatasourceAnnotationTransactionAttributeSource(TransactionAnnotationParser... annotationParsers) {
		super(annotationParsers);
	}

	/**
	 * @param annotationParser
	 */
	public MultiplyDatasourceAnnotationTransactionAttributeSource(TransactionAnnotationParser annotationParser) {
		super(annotationParser);
	}

}
