package com.zhangyao.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhangyao.entity.system.Permission;
import com.zhangyao.entity.system.User;
import com.zhangyao.service.PermissionService;
import com.zhangyao.service.UserService;

/**
 * @author zhangyao:
 * @date 创建时间：Nov 27, 2018 9:44:59 AM
 */
/*
 * 创建一个UserRealm类，并继承AuthorizingRealm， 用来给shiro注入认证信息和授权信息
 */
public class UserRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	/*
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权.........");
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		System.out.println("授权user:"+user);
		List<Permission> permissions = permissionService.selectPermissionByUserId(user.getUserId());
		Set<String> permission=new HashSet<>();
		for (Permission p : permissions) {
			permission.add(p.getResName());
		}
		info.addStringPermissions(permission);
		LOGGER.info("授权......");
		return info;
	}

	/*
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		System.out.println("认证.........");
		String username=(String) token.getPrincipal();
		System.out.println("username:"+ username);
		User user = userService.findByUserName(username);
/*		user.setFullName("张耀");
		user.setUserName("zhangyao");*/
		System.out.println("user:"+ user);
		if (user == null) {
			throw new UnknownAccountException();// 未找到账户
		}
		LOGGER.info("认证.....");
		System.out.println(user.getPassword());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user, 
				user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), 
				getName());
		return authenticationInfo;
	}

}
