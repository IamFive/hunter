/**
 * @(#)HostFilter.java 2014年4月8日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.shiro;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.google.common.collect.Maps;

import edu.hunter.modules.web.Servlets;

/**
 * @author Woo Cupid
 * @date 2014年4月8日
 * @version $Revision$
 */
public class HostFilter extends AuthorizationFilter {

	public static final String IPV4_QUAD_REGEX = "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2(?:[0-4][0-9]|5[0-5]))";

	public static final String IPV4_REGEX = "(?:" + IPV4_QUAD_REGEX + "\\.){3}" + IPV4_QUAD_REGEX + "$";
	public static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

	public static final String PRIVATE_CLASS_B_SUBSET = "(?:1[6-9]|2[0-9]|3[0-1])";

	public static final String PRIVATE_CLASS_A_REGEX = "10\\.(?:" + IPV4_QUAD_REGEX + "\\.){2}" + IPV4_QUAD_REGEX + "$";

	public static final String PRIVATE_CLASS_B_REGEX = "172\\." + PRIVATE_CLASS_B_SUBSET + "\\." + IPV4_QUAD_REGEX
			+ "\\." + IPV4_QUAD_REGEX + "$";

	public static final String PRIVATE_CLASS_C_REGEX = "192\\.168\\." + IPV4_QUAD_REGEX + "\\." + IPV4_QUAD_REGEX + "$";

	Map<String, Pattern> authorizedIps = Maps.newHashMap(); // user-configured IP (which can be wildcarded) to
															// constructed regex mapping
	Map<String, String> deniedIps;
	Map<String, String> authorizedHostnames;
	Map<String, String> deniedHostnames;

	public void setAuthorizedHosts(String authorizedHosts) {
		if (!StringUtils.hasText(authorizedHosts)) {
			throw new IllegalArgumentException("authorizedHosts argument cannot be null or empty.");
		}
		String[] hosts = StringUtils.tokenizeToStringArray(authorizedHosts, ", \t");

		for (String host : hosts) {
			// replace any periods with \\. to ensure the regex works:
			String periodsReplaced = host.replace(".", "\\.");
			// check for IPv4:
			String wildcardsReplaced = periodsReplaced.replace("*", IPV4_QUAD_REGEX);
			authorizedIps.put(host, Pattern.compile(wildcardsReplaced));
		}
	}

	public void setDeniedHosts(String deniedHosts) {
		if (!StringUtils.hasText(deniedHosts)) {
			throw new IllegalArgumentException("deniedHosts argument cannot be null or empty.");
		}
	}

	protected boolean isIpv4Candidate(String host) {
		String[] quads = StringUtils.tokenizeToStringArray(host, ".");
		if ((quads == null) || (quads.length != 4)) {
			return false;
		}
		for (String quad : quads) {
			if (!quad.equals("*")) {
				try {
					Integer.parseInt(quad);
				} catch (NumberFormatException nfe) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		String host = Servlets.getIp((HttpServletRequest) request);
		if (this.authorizedIps.isEmpty()) {
			String[] ips = (String[]) mappedValue;
			for (String ip : ips) {
				this.setAuthorizedHosts(ip);
			}
		}

		for (Pattern p : this.authorizedIps.values()) {
			Matcher matcher = p.matcher(host);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
}
