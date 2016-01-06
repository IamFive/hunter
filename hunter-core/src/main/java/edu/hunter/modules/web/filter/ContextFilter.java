/**
 * @(#)GlobalAttributeFilter.java 2015年6月11日
 *
 * Copyright 2008-2015 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.hunter.modules.web.Servlets;

/**
 * 
 * inject common contexts to request
 * 
 * <li>ctx -- project base path</li>
 * 
 * @author Woo Cupid
 * @date 2015年6月11日
 * @version $Revision$
 */
@Component(value = "ContextFilter")
public class ContextFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(ContextFilter.class);

	private static final String ATTR_CTX = "ctx";
	private static final String ATTR_ENV = "env";
	private static final String ATTR_VERSION = "v";

	@Resource(name = "properties")
	Properties properties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			request.setAttribute(ATTR_CTX, Servlets.getBasePath(request));
			
			String env = properties.get("pro.env").toString();
			String version = properties.get("pro.version").toString();
			request.setAttribute(ATTR_ENV, env);
			request.setAttribute(ATTR_VERSION, "debug".equals(env) ? String.valueOf(new Date().getTime()) : version);

		} catch (Exception e) {
			logger.error("Failed to setup common context", e);
		} finally {
			filterChain.doFilter(request, response);
		}
	}

}
