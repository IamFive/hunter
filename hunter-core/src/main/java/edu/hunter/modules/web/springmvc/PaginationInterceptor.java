/**
 * @(#)RequestPathInject.java 2013年11月7日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

import com.google.common.collect.Maps;

import edu.hunter.modules.web.Servlets;

/**
 * @author Woo Cupid
 * @date 2013年11月7日
 * @version $Revision$
 */
public class PaginationInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 
	 */

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	private static final String SEARCH_PARAMS_PREFIX = "search_";
	private static final String SEARCH_PARAMS_ENCODED = "_sps";

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
			boolean isRedirectView = (modelAndView.getView() != null)
					&& (modelAndView.getView() instanceof RedirectView);
			if (!isRedirectView && !modelAndView.getViewName().startsWith("redirect:")) {
				HashMap<String, Object> searchParmas = Maps.newHashMap();
				Enumeration paramNames = request.getParameterNames();
				while ((paramNames != null) && paramNames.hasMoreElements()) {
					String paramName = (String) paramNames.nextElement();
					if (paramName.startsWith(SEARCH_PARAMS_PREFIX)) {

						String[] values = request.getParameterValues(paramName);
						if ((values == null) || (values.length == 0)) {
							// Do nothing, no values found at all.
						} else if (values.length > 1) {
							searchParmas.put(paramName, values);
						} else if (StringUtils.isNotBlank(values[0])) {
							searchParmas.put(paramName, values[0]);
						}
					}
				}

				String sortby = request.getParameter(PaginationHelper.PAGINATION_ATTRNAME_SORT);
				modelAndView.addObject(PaginationHelper.PAGINATION_ATTRNAME_SORT, sortby);

				String encoded = Servlets.encodeParameterStringWithPrefix(searchParmas, null);
				if (StringUtils.isNotBlank(sortby)) {
					if (StringUtils.isNotBlank(encoded)) {
						encoded = encoded + "&sortby=" + sortby;
					} else {
						encoded = "sortby=" + sortby;
					}
				}

				if (StringUtils.isNotBlank(encoded)) {
					encoded = UriUtils.encodePath(encoded, "UTF-8");
				}

				modelAndView.addObject(SEARCH_PARAMS_ENCODED, encoded);

				// should read from pageImpl indeed. For now, we just return it here.
				modelAndView.addAllObjects(searchParmas);
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String encodePath = UriUtils.encodePath(
				"page=2&sortby=&search_LIKE_auctionName=周",
				"UTF-8");
		System.out.println(encodePath);
	}
}
