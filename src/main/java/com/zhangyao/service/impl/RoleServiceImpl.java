package com.zhangyao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangyao.entity.PageBean;
import com.zhangyao.entity.Role;
import com.zhangyao.mapper.RoleMapper;
import com.zhangyao.service.RoleService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 14, 2018 4:05:12 PM
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageBean getAllRole(int pageCode, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCode, pageSize);
		Page<Role> page = (Page<Role>) roleMapper.selectAll();
		return new PageBean(page.getResult(), 0, "成功", page.getTotal());
	}

	@Override
	public Role getRoleById(Integer roleId) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(roleId);
	}

}
