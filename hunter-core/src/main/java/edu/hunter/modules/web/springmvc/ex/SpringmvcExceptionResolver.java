/**
 * @(#)SpringmvcExceptionResolver.java 2014年3月13日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc.ex;

import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.handler.*;

/**
 * 
 * Just a resolver to log exception for all exception.
 * 
 * @author Woo Cupid
 * @date 2014年3月13日
 * @version $Revision$
 */
public class SpringmvcExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(SpringmvcExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.warn("exception not handled caught by springmvc", ex);
		return super.doResolveException(request, response, handler, ex);
	}

}
