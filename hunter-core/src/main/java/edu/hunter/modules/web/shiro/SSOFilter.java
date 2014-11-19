/**
 * @(#)AA.java 2013年11月23日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Woo Cupid
 * @date 2013年11月23日
 * @version $Revision$
 */
public class SSOFilter extends FormAuthenticationFilter {

	private static Logger logger = LoggerFactory.getLogger(SSOFilter.class);

	// the name of the parameter service ticket in url
	private static final String TICKET_PARAMETER = "ticket";
	private static final String USERID_PARAMETER = "userId";

	// the url where the application is redirected if the CAS service ticket validation failed (example :
	// /mycontextpatch/cas_error.jsp)
	private String failureUrl;

	/**
	 * The token created for this authentication is a CasToken containing the CAS service ticket received on the CAS
	 * service url (on which
	 * the filter must be configured).
	 * 
	 * @param request the incoming request
	 * @param response the outgoing response
	 * @throws Exception if there is an error processing the request.
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String ticket = httpRequest.getParameter(TICKET_PARAMETER);
		String userId = httpRequest.getParameter(USERID_PARAMETER);
		return new SSOToken(ticket, userId, httpRequest, httpResponse);
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// assume user has login, then he require login again, we need to logout first
		Subject subject = SecurityUtils.getSubject();
		if (isLoginRequest(request, response) && subject.isAuthenticated()) {
			subject.logout();
		}

		return super.onPreHandle(request, response, mappedValue);
	}
}
