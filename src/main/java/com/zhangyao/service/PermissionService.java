package com.zhangyao.service;

import java.util.List;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.TreeEntity;
import com.zhangyao.entity.User;
import com.zhangyao.entity.Xtree;

/**
* @author zhangyao:
* @date 创建时间：Dec 12, 2018 3:33:39 PM
*/
public interface PermissionService {
	
	 public List<Permission> selectMenusByUser(User user);
	
	//根据用户id获取权限
	 public List<Permission> selectPermissionByUserId(long userId);
	 
	 //根据父节点的ID获取所有子节点
	 public List<Permission> getChildPerms(List<Permission> list, int parentId);
	 
	 //递归列表
	 void recursionFn(List<Permission> list, Permission permission);
	 
	 //得到子节点列表
	 List<Permission> getChildList(List<Permission> list, Permission permission);
	 
	 //判断是否有子节点
	 boolean hasChild(List<Permission> list, Permission permission);
	 
	 //根据角色id获取权限
	 public List<Xtree> selectPermissionByRoleId(long roleId);
	 
	 public List<Xtree> getAll();
	 
	 //递归查找子菜单
	 List<Xtree> getChild(int id, List<Permission> rootMenu);
	 
	 
}
