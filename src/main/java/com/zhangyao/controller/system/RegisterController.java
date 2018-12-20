package com.zhangyao.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.system.Result;
import com.zhangyao.entity.system.User;
import com.zhangyao.mapper.UserMapper;
import com.zhangyao.service.UserService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 13, 2018 9:45:04 AM
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	// 注册用户
	@RequestMapping("")
	public String register() {
		System.out.println("register()");
		return "register";
	}


	@RequestMapping("/test")
	@ResponseBody
	public void test() {
	System.out.println(userMapper.selectUserByName("zhangyao"));
	}

	@PostMapping("/create")
	@ResponseBody
	public Result createUser(@RequestBody User user) {
		System.out.println(user);
		try {
			if(userService.findByUserName(user.getUserName())==null) {
				user.setSex(0);
				userService.createUser(user);
				Long userId=userService.findByUserName(user.getUserName()).getUserId();
				userService.createUserRole(userId, Long.valueOf(1));
				return new Result(true, "success");
			}else {
				return new Result(false, "exist");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "fail");
		}
	}

	@PostMapping("/checkUser")
	@ResponseBody
	public Result checkUser(@RequestParam("userName") String userName) {

		User user = userService.findByUserName(userName);
		System.out.println(user);
		if (user == null) {
			return new Result(true, "notexist");
		} else {
			return new Result(false, "exist");
		}
	}
}
