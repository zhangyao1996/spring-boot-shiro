package com.zhangyao.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.zhangyao.entity.system.PageBean;
import com.zhangyao.entity.system.Result;
import com.zhangyao.entity.system.User;
import com.zhangyao.service.RoleService;
import com.zhangyao.service.UserService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 13, 2018 9:07:53 AM
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/userList")
	public String userList() {
		return "user/userlist";
	}

	/**
	 * 分页查询
	 *
	 * @param goods
	 *            查询条件
	 * @param pageCode
	 *            当前页
	 * @param pageSize
	 *            每页显示的记录数
	 * @return
	 */
	@RequestMapping("/findByConPage")
	@ResponseBody
	public PageBean findByConPage(@RequestParam(value = "keyword", required = false) String username,
			@RequestParam(value = "page", required = false) int pageCode,
			@RequestParam(value = "limit", required = false) int pageSize) {
		System.out.println(username);
		System.out.println("findByConPage()");
		return userService.findByPage(username, pageCode, pageSize);
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("roleList", roleService.getAll());
		return "user/add";
	}

	@PostMapping("/checkUser")
	@ResponseBody
	public Result checkUser(@RequestParam("userName") String userName) {
		User user = userService.findByUserName(userName);
		if (user == null) {
			return new Result(true, "用户名不存在");
		} else {
			return new Result(false, "用户名存在");
		}
	}

	@PostMapping("/add")
	@ResponseBody
	public Result addUser(@RequestBody(required = false) User user) {
		System.out.println("adduser:" + user);
		User user1 = userService.findByUserName(user.getUserName());
		if (user1 != null) {
			return new Result(false, "添加失败，用户名存在");
		} else {
			try {
				userService.createUser(user);
				User u = userService.findByUserName(user.getUserName());
				System.out.println("u:" + u);
				if (u != null) {
					userService.createUserRole(u.getUserId(), user.getRoleId());
				}
				return new Result(true, "添加成功");
			} catch (Exception e) {
				// TODO: handle exception
				return new Result(false, "添加失败，未知错误");
			}

		}
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("userId") Long userId, Model model) {
	
		User user = userService.findUserById(userId);
		String roleName=userService.findRoleNameByUserId(userId);
		model.addAttribute("roleName", roleName);
		model.addAttribute("user", user);
		return "user/detail";
	}

	@DeleteMapping("/del")
	@ResponseBody
	public Result deleteUserById(Long userId) {
		
		try {
			userService.deleteUserById(userId);
			userService.deleteUserRoleByUserId(userId);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "删除失败");
		}
	}

	@GetMapping("/edit")
	public String edit(Long userId, Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("roleList", roleService.getAll());
		model.addAttribute("user", user);
		return "user/edit";
	}

	@PostMapping("/edit")
	@ResponseBody
	public Result edit(@RequestBody(required = false) User user) {
		System.out.println("editUser2:" + user);
		try {
			userService.editUse(user);
			Long userId=user.getUserId();
			Long roleId=user.getRoleId();
			userService.updateRoleIdByUserId(roleId, userId);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "修改失败");
		}

	}

	// 批量删除
	@RequestMapping("/deleteUserIds")
	@ResponseBody
	public Result deleteUserByIds(@RequestParam("ids") String ids) {
		String[] id = ids.split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				System.out.println(id[i]);
				Long num = Long.valueOf(id[i]);
				userService.deleteUserById(num);
				userService.deleteUserRoleByUserId(num);
			}
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "删除失败");
		}

	}
}
