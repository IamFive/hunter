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
 * New Oauth2 sso token
 * 
 * @author Woo Cupid
 * @date 2013年11月23日
 * @version $Revision$
 */
public class Oauth2Token implements RememberMeAuthenticationToken, HostAuthenticationToken {

	private static final long serialVersionUID = 8587329689973009598L;

	// is the user in a remember me mode ?
	private boolean isRememberMe = true;

	private HttpServletRequest request;

	@SuppressWarnings("unused")
	private HttpServletResponse response;

	private String accessToken;

	public Oauth2Token(String accessToken) {
		this.accessToken = accessToken;
	}

	public Oauth2Token(String accessToken, HttpServletRequest request, HttpServletResponse response) {
		this.accessToken = accessToken;
		this.request = request;
		this.response = response;
	}

	@Override
	public Object getPrincipal() {
		return accessToken;
	}

	@Override
	public Object getCredentials() {
		return accessToken;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
