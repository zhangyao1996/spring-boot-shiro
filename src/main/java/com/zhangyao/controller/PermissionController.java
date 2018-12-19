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
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Result;
import com.zhangyao.entity.TreeTable;
import com.zhangyao.service.PermissionService;

@Controller
@RequestMapping("/pers")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/persList")
	public String persList() {

		return "pers/perslist";
	}

	@GetMapping("/perstable")
	@ResponseBody
	public TreeTable persLists() {
		List<Permission> permissions = permissionService.getAllPers();
		TreeTable treeTable = new TreeTable();
		treeTable.setMsg("true");
		treeTable.setCode(0);
		treeTable.setCount(permissions.size());
		treeTable.setData(permissions);
		return treeTable;
	}

	@DeleteMapping("del")
	@ResponseBody
	public Result delPers(Long id) {
		System.out.println(id);
		try {
			permissionService.deletePersById(id);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "删除失败");
		}

	}

	@GetMapping("/add")
	public String addPers(Long id, Model model) {
		Permission permission = permissionService.getPersById(id);
		System.out.println("id"+id);
		System.out.println("perm"+permission);
		if (permission == null) {
			permission=new Permission();
			permission.setId(Long.valueOf(0));
			permission.setResName("无父级菜单");
		}
		model.addAttribute("per", permission);
		return "pers/add";
	}

	@PostMapping("/add")
	@ResponseBody
	public Result addPers(@RequestBody(required = false) Permission permission) {
		System.out.println(permission);
		try {
			permissionService.createPer(permission);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "添加失败");
		}

	}
	
	@GetMapping("/edit")
	public String editPers(Long id, Model model) {
		Permission permission = permissionService.getPersById(id);
		System.out.println("id"+id);
		System.out.println("perm"+permission);
		if (permission == null) {
			permission=new Permission();
			permission.setId(Long.valueOf(0));
			permission.setResName("无父级菜单");
		}
		model.addAttribute("per", permission);
		return "pers/edit";
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public Result editPers(@RequestBody(required = false) Permission permission) {
		System.out.println(permission);
		try {
			permissionService.editPer(permission);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, "修改失败");
		}

	}
}
