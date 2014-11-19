package edu.hunter.modules.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import edu.hunter.modules.utils.ReflectionUtils;

/**
 * @author qianbiao.wu
 * @date 2011-12-22
 * @version $Revision$
 */
public abstract class BaseTester<T> {

	protected Logger logger = LoggerFactory.getLogger(getClazz());

	protected static ResourceLoader resourceLoader = new DefaultResourceLoader();

	protected Class<?> getClazz() {
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}
}
