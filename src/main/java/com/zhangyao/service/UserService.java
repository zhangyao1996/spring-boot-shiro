package com.zhangyao.service;
/**
* @author zhangyao:
* @date 创建时间：Dec 12, 2018 3:19:57 PM
*/

import com.zhangyao.entity.system.PageBean;
import com.zhangyao.entity.system.User;

public interface UserService {
	
	User findByUserName(String userName);
	
	void createUser(User user);

	PageBean findByPage(String username, int pageCode, int pageSize);
	
	User findUserById(Long userId);
	
	void deleteUserById(Long userId);
	
	void editUse(User user);
	
	void createUserRole(Long userId,Long roleId);
	
	public String findRoleNameByUserId(Long userId);
	
	void updateRoleIdByUserId(Long roleId,Long userId);
	
	void deleteUserRoleByUserId(Long userId);
	
	
}
