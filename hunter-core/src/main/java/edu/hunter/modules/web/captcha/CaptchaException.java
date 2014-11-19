/**
 * @(#)CaptchaException.java 2014年7月8日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.captcha;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Woo Cupid
 * @date 2014年7月8日
 * @version $Revision$
 */
public class CaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super();
	}

	public CaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaException(String message) {
		super(message);
	}

	public CaptchaException(Throwable cause) {
		super(cause);
	}

}