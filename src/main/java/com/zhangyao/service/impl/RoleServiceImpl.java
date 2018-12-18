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
	
	@Override
	public Role getRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return roleMapper.selectRoleByName(roleName);
	}

	@Override
	public void CreateRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.insert(role);
		Role role2=roleMapper.selectOne(role);
		
		String[] nodes=role.getNodes().split(",");
		if(role2!=null) {
			for(int i=0;i<nodes.length;i++) {
				long roleId=role2.getRoleId();
				long permissionId=Long.valueOf(nodes[i]);		
				roleMapper.addRolePers(roleId, permissionId);
			}
		}
	}

	@Override
	public void deleteRoleById(long roleId) {
		// TODO Auto-generated method stub
		roleMapper.deleteByPrimaryKey(roleId);
		roleMapper.deleteRolePers(roleId);
	}

	@Override
	public void editRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateByPrimaryKey(role);
		long roleId=role.getRoleId();
		String[] nodes=role.getNodes().split(",");
		roleMapper.deleteRolePers(roleId);
		for(int i=0;i<nodes.length;i++) {
			long permissionId=Long.valueOf(nodes[i]);
			roleMapper.addRolePers(roleId, permissionId);
		}
	}
	
	
	
}
