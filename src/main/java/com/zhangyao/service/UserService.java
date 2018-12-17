package com.zhangyao.service;
/**
* @author zhangyao:
* @date 创建时间：Dec 12, 2018 3:19:57 PM
*/

import com.zhangyao.entity.PageBean;
import com.zhangyao.entity.User;

public interface UserService {
	
	User findByUserName(String userName);
	
	void createUse(User user);

	PageBean findByPage(String username, int pageCode, int pageSize);
	
	User findUserById(Integer userId);
	
	void deleteUserById(Integer userId);
	
	void editUse(User user);
}
