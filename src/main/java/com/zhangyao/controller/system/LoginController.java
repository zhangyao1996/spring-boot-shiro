package com.zhangyao.controller.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.system.Permission;
import com.zhangyao.entity.system.Result;
import com.zhangyao.entity.system.User;
import com.zhangyao.service.PermissionService;
import com.zhangyao.service.UserService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:52:17 PM
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * get请求，登录页面跳转
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		/*
		 * // 如果已经认证通过，直接跳转到首页 if (SecurityUtils.getSubject().isAuthenticated()) {
		 * return "redirect:/index"; }
		 */
		return "login";
	}
	

	/**
	 * post表单提交，登录
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public Result login(@RequestBody User user) {
		// System.out.println("user:" + user);
		Subject user1 = SecurityUtils.getSubject();
		String username = user.getUserName();
		String password = user.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			// shiro帮我们匹配密码什么的，我们只需要把东西传给它，它会根据我们在UserRealm里认证方法设置的来验证
			user1.login(token);
			return new Result(true, "success");
		} catch (UnknownAccountException e) {
			// 账号不存在和下面密码错误一般都合并为一个账号或密码错误，这样可以增加暴力破解难度

			// model.addAttribute("message", "账号不存在！");
			return new Result(false, "账号不存在！");
		} catch (DisabledAccountException e) {
			// model.addAttribute("message", "账号未启用！");
			return new Result(false, "账号未启用！");
		} catch (IncorrectCredentialsException e) {
			// model.addAttribute("message", "密码错误！");
			return new Result(false, "密码错误！");
		} catch (Throwable e) {
			// model.addAttribute("message", "未知错误！");
			return new Result(false, "未知错误！");
		}

	}

	/**
	 * 首页，并将登录用户的全名返回前台
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user != null) {
			List<Permission> permissions=permissionService.selectMenusByUser(user);
			model.addAttribute("permissions", permissions);
			model.addAttribute("username", user.getFullName());
			return "index";
		} else {
			return "login";
		}

	}
	
	/*@PostMapping("/getMenu")
	@ResponseBody
	public List<Permission> selectPermissionByUserId(@RequestParam("userId") long userId) {
		return permissionService.selectPermissionByUserId(userId);
	}*/

	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "login";
	}
}
