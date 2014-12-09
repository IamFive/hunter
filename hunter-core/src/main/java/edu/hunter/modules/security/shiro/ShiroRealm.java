/**
 * @(#)ShiroRealm.java 2013年10月28日
 * 
 * Copyright 2008-2013 by Woo Cupid.
 *
 * All rights reserved.
 * 
 */

package edu.hunter.modules.security.shiro;

import javax.annotation.*;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.*;
import org.apache.shiro.realm.*;
import org.apache.shiro.subject.*;
import org.slf4j.*;

/**
 * 
 * @author Woo Cupid
 * @date 2014年5月19日
 * @version $Revision$
 */
public class ShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		/**
		 * User user = userService.findUserByMobile(token.getUsername());
		 * if (user != null) {
		 * byte[] salt = Encodes.decodeHex(user.getSalt());
		 * return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getMobile()), user.getPassword(),
		 * ByteSource.Util.bytes(salt), getName());
		 * } else {
		 * return null;
		 * }
		 **/

		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		// HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		// matcher.setHashIterations(UserService.HASH_INTERATIONS);
		// setCredentialsMatcher(matcher);
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}

	@Override
	public void doClearCache(PrincipalCollection principals) {
		super.doClearCache(principals);
		clearCachedAuthenticationInfo(principals);
		clearCachedAuthorizationInfo(principals);
	}

}
