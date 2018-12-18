package com.zhangyao.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
		System.out.println("roleList()");
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
		List<Xtree> xtrees = permissionService.selectPermissionByRoleId(roleId);
		return xtrees;
	}

	@GetMapping("/add")
	public String addRole() {
		return "role/add";
	}

	/*
	 * @RequestBody(required = false)Role对象不能为空
	 * 
	 * @Transactional //事务回滚出现 There was an unexpected error (type=Not Found,
	 * status=404).
	 */
	@PostMapping("/add")
	@ResponseBody
	public Result addRole(@RequestBody(required = false) Role role) {
		String roleName = role.getRoleName();
		String nodes = role.getNodes();

		System.out.println(role);
		if (roleService.getRoleByName(roleName) != null) {
			return new Result(false, "添加失败,角色名已存在");
		} else if (nodes == "") {
			return new Result(false, "添加失败,权限路径不能为空");
		} else {
			try {
				roleService.CreateRole(role);
				return new Result(true, "添加成功");
			} catch (Exception e) {
				// TODO: handle exception
				return new Result(false, "添加失败，未知错误");
			}
		}

	}

	@DeleteMapping("/del")
	@ResponseBody
	public Result deleteRole(Integer roleId) {
		
		System.out.println(roleId);
		try {
			roleService.deleteRoleById(roleId);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "删除失败");
		}
	}

	@GetMapping("/edit")
	public String editRole(Integer roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "role/edit";
	}

	@PostMapping("/edit")
	@ResponseBody
	public Result editRole(@RequestBody(required = false) Role role) {
		String roleName = role.getRoleName();
		String nodes = role.getNodes();

		System.out.println(role);
		if (nodes == "") {
			return new Result(false, "修改失败,权限路径不能为空");
		} else {
			try {
				roleService.editRole(role);
				return new Result(true, "修改成功");
			}catch (Exception e) {
				// TODO: handle exception
				return new Result(false, "修改失败");
			}
		}
		
	}

}
