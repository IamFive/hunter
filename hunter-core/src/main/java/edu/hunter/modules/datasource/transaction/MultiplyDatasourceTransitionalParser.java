/**
 * @(#)MultiplyDatasourceTransitionalParsor.java 2013年12月17日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource.transaction;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @see org.springframework.transaction.annotation.SpringTransactionAnnotationParser
 * @author Woo Cupid
 * @date 2013年12月17日
 * @version $Revision$
 */
@SuppressWarnings("serial")
public class MultiplyDatasourceTransitionalParser implements TransactionAnnotationParser, Serializable {

	@Override
	public TransactionAttribute parseTransactionAnnotation(AnnotatedElement ae) {
		MultiplyDatasourceTransitional ann = AnnotationUtils.getAnnotation(ae, MultiplyDatasourceTransitional.class);
		if (ann != null) {
			return parseTransactionAnnotation(ann);
		} else {
			return null;
		}
	}

	public TransactionAttribute parseTransactionAnnotation(MultiplyDatasourceTransitional ann) {
		MultiplyDatasourceTransactionAttribute mdta = new MultiplyDatasourceTransactionAttribute();

		mdta.setDbName(ann.dbName()); // setup db name

		mdta.setPropagationBehavior(ann.propagation().value());
		mdta.setIsolationLevel(ann.isolation().value());
		mdta.setTimeout(ann.timeout());
		mdta.setReadOnly(ann.readOnly());
		mdta.setQualifier(ann.value());
		ArrayList<RollbackRuleAttribute> rollBackRules = new ArrayList<RollbackRuleAttribute>();
		Class[] rbf = ann.rollbackFor();
		for (Class rbRule : rbf) {
			RollbackRuleAttribute rule = new RollbackRuleAttribute(rbRule);
			rollBackRules.add(rule);
		}
		String[] rbfc = ann.rollbackForClassName();
		for (String rbRule : rbfc) {
			RollbackRuleAttribute rule = new RollbackRuleAttribute(rbRule);
			rollBackRules.add(rule);
		}
		Class[] nrbf = ann.noRollbackFor();
		for (Class rbRule : nrbf) {
			NoRollbackRuleAttribute rule = new NoRollbackRuleAttribute(rbRule);
			rollBackRules.add(rule);
		}
		String[] nrbfc = ann.noRollbackForClassName();
		for (String rbRule : nrbfc) {
			NoRollbackRuleAttribute rule = new NoRollbackRuleAttribute(rbRule);
			rollBackRules.add(rule);
		}
		mdta.getRollbackRules().addAll(rollBackRules);
		return mdta;
	}

	@Override
	public boolean equals(Object other) {
		return ((this == other) || (other instanceof MultiplyDatasourceTransitionalParser));
	}

	@Override
	public int hashCode() {
		return MultiplyDatasourceTransitionalParser.class.hashCode();
	}

	/**
	 * customer transaction attribute
	 * 
	 * @author Woo Cupid
	 * @date 2013年12月17日
	 * @version $Revision$
	 */
	public static class MultiplyDatasourceTransactionAttribute extends RuleBasedTransactionAttribute {

		public String dbName;

		/**
		 * @return the dbName
		 */
		public String getDbName() {
			return dbName;
		}

		/**
		 * @param dbName the dbName to set
		 */
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

	}

}
