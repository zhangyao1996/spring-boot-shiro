package com.zhangyao.service;
/**
* @author zhangyao:
* @date 创建时间：Dec 14, 2018 4:04:13 PM
*/



import java.util.List;

import com.zhangyao.entity.system.PageBean;
import com.zhangyao.entity.system.Role;


public interface RoleService {
	PageBean getAllRole(int pageCode, int pageSize);
	
	Role getRoleById(Integer roleId);
	
	Role getRoleByName(String roleName);
	
	void CreateRole(Role role);
	
	void deleteRoleById(long roleId);
	
	void editRole(Role role);
	
	List<Role> getAll();
}
