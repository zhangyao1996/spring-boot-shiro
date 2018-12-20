package com.zhangyao.controller.set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.system.Result;
import com.zhangyao.entity.system.User;
import com.zhangyao.service.UserService;

@Controller
@RequestMapping("/set")
public class UserControlle {
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public String getUser(Model model) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		System.out.println(user);
		model.addAttribute("user", user);
		return "set/user";
	}

	@PostMapping("/user")
	@ResponseBody
	public Result editUser(@RequestBody(required = false) User user) {
		try {
			userService.editUse(user);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "修改失败");
		}

	}

	@GetMapping("/delete")
	public String deleteUser(Model model) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);

		return "set/delete";
	}

	@RequestMapping("'delete")
	@ResponseBody
	public Result deleteUser(Long userId) {
		try {
			userService.deleteUserById(userId);
			userService.deleteUserRoleByUserId(userId);
			return new Result(true, "注销成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "注销失败");
		}

	}

}
