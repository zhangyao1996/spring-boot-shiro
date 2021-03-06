package com.zhangyao.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangyao:
 * @date 创建时间：Nov 27, 2018 9:54:45 AM
 */
@Configuration
public class ShiroConfig {
	/**
	 * 凭证匹配器
	 *
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// md5加密1次
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(1);
		return hashedCredentialsMatcher;
	}

	/**
	 * 自定义realm
	 *
	 * @return
	 */
	@Bean
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return userRealm;
	}

	/**
	 * 安全管理器 注：使用shiro-spring-boot-starter
	 * 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
	 *
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm());
		return securityManager;
	}

	/**
	 * 设置过滤规则
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

		// 注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
		// 所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/lib/**", "anon");//lib/css
        filterChainDefinitionMap.put("/js/**", "anon");//js
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/register/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");

		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

}
