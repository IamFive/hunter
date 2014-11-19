package edu.hunter.modules.persistence.mybatis;

import java.lang.annotation.*;

import org.springframework.stereotype.*;

/**
 * 标识MyBatis的DAO, 方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * 
 * @author Woo Cupid
 * @date 2014年10月24日
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatis {
	String value() default "";
}
