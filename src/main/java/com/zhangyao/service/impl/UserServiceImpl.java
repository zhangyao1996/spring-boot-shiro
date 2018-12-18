package com.zhangyao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangyao.entity.PageBean;
import com.zhangyao.entity.User;
import com.zhangyao.mapper.UserMapper;
import com.zhangyao.service.UserService;
import com.zhangyao.utils.PasswordHelper;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:22:05 PM
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordHelper passwordHelper;

	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userMapper.selectUserByName(userName);
	}

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		// 给user对象的密码做加密
		passwordHelper.encryptPassword(user);
		System.out.println("newUser:" + user);
		// 创建新用户
		userMapper.insert(user);
	}

	/**
	 * 分页查询-条件查询方法
	 *
	 * @param goods
	 *            查询条件
	 * @param pageCode
	 *            当前页
	 * @param pageSize
	 *            每页的记录数
	 * @return
	 */
	public PageBean findByPage(String username, int pageCode, int pageSize) {
		// 使用Mybatis分页插件
		PageHelper.startPage(pageCode, pageSize);

		// 调用分页查询方法，其实就是查询所有数据，mybatis自动帮我们进行分页计算
		Page<User> page = userMapper.findByPage(username);

		return new PageBean(page.getResult(), 0, "成功", page.getTotal());
	}

	@Override
	public User findUserById(Long userId) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public void deleteUserById(Long userId) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(userId);
		
	}

	@Override
	public void editUse(User user) {
		// TODO Auto-generated method stub
		passwordHelper.encryptPassword(user);
		System.out.println("newUser:"+user);
		//修改用户
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void createUserRole(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		userMapper.createUserRole(userId, roleId);
	}
	
	@Override
	public String findRoleNameByUserId(Long userId) {
		return userMapper.findRoleNameByUserId(userId);
	}

	@Override
	public void updateRoleIdByUserId(Long roleId, Long userId) {
		// TODO Auto-generated method stub
		userMapper.updateRoleIdByUserId(roleId, userId);
	}

	@Override
	public void deleteUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		userMapper.deleteUserRoleByUserId(userId);
	}
	
	
	
	
}
