/**
 * @(#)SSOToken.java 2013年11月23日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import edu.hunter.modules.web.Servlets;

/**
 * copy from Cas-shiro implemention
 * 
 * @author Woo Cupid
 * @date 2013年11月23日
 * @version $Revision$
 */
public class SSOToken implements RememberMeAuthenticationToken, HostAuthenticationToken {

	private static final long serialVersionUID = 8587329689973009598L;

	// the service ticket returned by the CAS server
	private String ticket = null;

	// the user identifier
	private String userId = null;

	// is the user in a remember me mode ?
	private boolean isRememberMe = false;

	private HttpServletRequest request;

	@SuppressWarnings("unused")
	private HttpServletResponse response;

	public SSOToken(String ticket, String userId) {
		this.ticket = ticket;
		this.userId = userId;
	}

	public SSOToken(String ticket, String userId, HttpServletRequest request, HttpServletResponse response) {
		this.ticket = ticket;
		this.userId = userId;
		this.request = request;
		this.response = response;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public Object getCredentials() {
		return ticket;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	@Override
	public boolean isRememberMe() {
		return isRememberMe;
	}

	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.authc.HostAuthenticationToken#getHost()
	 */
	@Override
	public String getHost() {
		if (this.request != null) {
			return Servlets.getIp(this.request);
		}
		return null;
	}
}
