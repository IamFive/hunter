/**
 * @(#)AutoRouteTransitional.java 2013年12月17日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.datasource.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

/**
 * @author Woo Cupid
 * @date 2013年12月17日
 * @version $Revision$
 * @see org.springframework.transaction.annotation.Transactional
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MultiplyDatasourceTransitional {

	/**
	 * identified which database will be routed to
	 * 
	 * @return
	 */
	String dbName() default "";

	/**
	 * A qualifier value for the specified transaction.
	 * <p>
	 * May be used to determine the target transaction manager, matching the qualifier value (or the bean name) of a
	 * specific {@link org.springframework.transaction.PlatformTransactionManager} bean definition.
	 */
	String value() default "";

	/**
	 * The transaction propagation type.
	 * Defaults to {@link Propagation#REQUIRED}.
	 * 
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
	 */
	Propagation propagation() default Propagation.REQUIRED;

	/**
	 * The transaction isolation level.
	 * Defaults to {@link Isolation#DEFAULT}.
	 * 
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#getIsolationLevel()
	 */
	Isolation isolation() default Isolation.DEFAULT;

	/**
	 * The timeout for this transaction.
	 * Defaults to the default timeout of the underlying transaction system.
	 * 
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#getTimeout()
	 */
	int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

	/**
	 * {@code true} if the transaction is read-only.
	 * Defaults to {@code false}.
	 * <p>
	 * This just serves as a hint for the actual transaction subsystem; it will <i>not necessarily</i> cause failure of
	 * write access attempts. A transaction manager which cannot interpret the read-only hint will <i>not</i> throw an
	 * exception when asked for a read-only transaction.
	 * 
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#isReadOnly()
	 */
	boolean readOnly() default false;

	/**
	 * Defines zero (0) or more exception {@link Class classes}, which must be a
	 * subclass of {@link Throwable}, indicating which exception types must cause
	 * a transaction rollback.
	 * <p>
	 * This is the preferred way to construct a rollback rule, matching the exception class and subclasses.
	 * <p>
	 * Similar to
	 * {@link org.springframework.transaction.interceptor.RollbackRuleAttribute#RollbackRuleAttribute(Class clazz)}
	 */
	Class<? extends Throwable>[] rollbackFor() default {};

	/**
	 * Defines zero (0) or more exception names (for exceptions which must be a
	 * subclass of {@link Throwable}), indicating which exception types must cause
	 * a transaction rollback.
	 * <p>
	 * This can be a substring, with no wildcard support at present. A value of "ServletException" would match
	 * {@link javax.servlet.ServletException} and subclasses, for example.
	 * <p>
	 * <b>NB: </b>Consider carefully how specific the pattern is, and whether to include package information (which
	 * isn't mandatory). For example, "Exception" will match nearly anything, and will probably hide other rules.
	 * "java.lang.Exception" would be correct if "Exception" was meant to define a rule for all checked exceptions. With
	 * more unusual {@link Exception} names such as "BaseBusinessException" there is no need to use a FQN.
	 * <p>
	 * Similar to
	 * {@link org.springframework.transaction.interceptor.RollbackRuleAttribute#RollbackRuleAttribute(String exceptionName)}
	 */
	String[] rollbackForClassName() default {};

	/**
	 * Defines zero (0) or more exception {@link Class Classes}, which must be a
	 * subclass of {@link Throwable}, indicating which exception types must <b>not</b>
	 * cause a transaction rollback.
	 * <p>
	 * This is the preferred way to construct a rollback rule, matching the exception class and subclasses.
	 * <p>
	 * Similar to
	 * {@link org.springframework.transaction.interceptor.NoRollbackRuleAttribute#NoRollbackRuleAttribute(Class clazz)}
	 */
	Class<? extends Throwable>[] noRollbackFor() default {};

	/**
	 * Defines zero (0) or more exception names (for exceptions which must be a
	 * subclass of {@link Throwable}) indicating which exception types must <b>not</b>
	 * cause a transaction rollback.
	 * <p>
	 * See the description of {@link #rollbackForClassName()} for more info on how the specified names are treated.
	 * <p>
	 * Similar to
	 * {@link org.springframework.transaction.interceptor.NoRollbackRuleAttribute#NoRollbackRuleAttribute(String exceptionName)}
	 */
	String[] noRollbackForClassName() default {};

}
