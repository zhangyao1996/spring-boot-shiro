package com.zhangyao.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.PageBean;
import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Result;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.Xtree;
import com.zhangyao.service.PermissionService;
import com.zhangyao.service.RoleService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 14, 2018 3:36:55 PM
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/roleList")
	public String roleList() {

		return "role/roleList";
	}

	@RequestMapping("/findRoles")
	@ResponseBody
	public PageBean findRoles(@RequestParam(value = "page", required = false) int pageCode,
			@RequestParam(value = "limit", required = false) int pageSize) {
		System.out.println("findRoles()");
		return roleService.getAllRole(pageCode, pageSize);
	}

	@RequestMapping("/detail")
	public String roleDetail(Integer roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "role/detail";
	}
	
	@GetMapping("/getAll")
	@ResponseBody
	public List<Xtree> getAllPer() {
		return permissionService.getAll();
	}

	@GetMapping("/getPermissions")
	@ResponseBody
	public List<Xtree> getPermissionsByRoleId(@RequestParam("roleId") Long roleId) {
		List<Xtree> xtrees=permissionService.selectPermissionByRoleId(roleId);
		return xtrees;
	}
	
	@GetMapping("/add")
	public String addRole() {
		return "role/add";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Result addRole(Role role) {
		return new Result(true, "添加成功");
		
	}

}
