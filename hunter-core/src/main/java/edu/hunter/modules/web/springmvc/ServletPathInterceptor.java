/**
 * @(#)RequestPathInject.java 2013年11月7日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.hunter.modules.web.Servlets;

/**
 * @author Woo Cupid
 * @date 2013年11月7日
 * @version $Revision$
 */
public class ServletPathInterceptor extends HandlerInterceptorAdapter {

	private static final String CTX_URI_ATTR = "ctx";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);

		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			boolean isRedirectView = StringUtils.startsWithIgnoreCase(viewName, "redirect:");
			if (!isRedirectView) {
				modelAndView.getModelMap().addAttribute(CTX_URI_ATTR, Servlets.getBasePath(request));
			}
		}
	}
}
