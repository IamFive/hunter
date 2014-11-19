/**
 * @(#)RealServerInterceptor.java 2014年5月12日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Woo Cupid
 * @date 2014年5月12日
 * @version $Revision$
 */
public class RealServerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String host = SecurityUtils.getSubject().getSession().getHost();
		response.addHeader("Nginx-Server", host + ":" + request.getServerPort());
		return super.preHandle(request, response, handler);
	}

}
