package com.zhangyao.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangyao.entity.system.Permission;
import com.zhangyao.entity.system.User;
import com.zhangyao.entity.system.Xtree;
import com.zhangyao.mapper.PermissionMapper;
import com.zhangyao.service.PermissionService;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:34:42 PM
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public List<Permission> selectMenusByUser(User user) {
		// TODO Auto-generated method stub
		List<Permission> permissions = new LinkedList<>();
		permissions = permissionMapper.selectPermissionByUserId(user.getUserId());
		return getChildPerms(permissions, 0);
	}

	@Override
	public List<Permission> selectPermissionByUserId(long userId) {
		// TODO Auto-generated method stub
		return permissionMapper.selectPermissionByUserId(userId);
	}

	@Override
	public List<Xtree> selectPermissionByRoleId(long roleId) {
		// TODO Auto-generated method stub

		List<Permission> permissions = permissionMapper.selectPermissionByRoleId(roleId);
		List<Xtree> xtrees = getAll();
		// ?
		return getPer(xtrees, permissions);
	}

	public List<Xtree> getPer(List<Xtree> xtrees, List<Permission> permissions) {
		for (int i = 0; i < xtrees.size(); i++) {
			for (Permission permission : permissions) {
				if (permission.getId() == Long.valueOf(xtrees.get(i).getValue())) {
					xtrees.get(i).setChecked(true);
				}
			}
			if (xtrees.get(i).getData() == null) {
				System.out.println("kong");
				xtrees.get(i).setData(new LinkedList<>());
			} else {	
				if (xtrees.get(i).getData().size() > 0) {
					getPer(xtrees.get(i).getData(), permissions);
				}
			}

		}
		return xtrees;
	}

	@Override
	public List<Xtree> getAll() {
		// TODO Auto-generated method stub
		List<Xtree> xtrees = new LinkedList<>();
		List<Permission> permissions = permissionMapper.selectAll();
		// 先找到所有的一级菜单
		for (Permission permission : permissions) {
			// 一级菜单没有parentId
			if (permission.getParentId() == 0) {
				Xtree xtree = new Xtree();
				xtree.setTitle(permission.getResName());
				xtree.setValue(permission.getId() + "");
				xtrees.add(xtree);
			}
		}
		// 为一级菜单设置子菜单，getChild是递归调用的
		for (Xtree xtree : xtrees) {
			xtree.setData(getChild(Integer.valueOf(xtree.getValue()), permissions));
		}
		return xtrees;
	}

	// 递归查询子菜单
	@Override
	public List<Xtree> getChild(int id, List<Permission> rootMenu) {
		// TODO Auto-generated method stub

		List<Xtree> childList = new LinkedList<>();
		for (Permission permission : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (permission.getParentId() == id) {
				Xtree xtree = new Xtree();
				xtree.setTitle(permission.getResName());
				xtree.setValue(permission.getId() + "");
				xtree.setData(new ArrayList<>());
				childList.add(xtree);
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (Xtree menu : childList) {
			// 判断是否是末级节点
			if (endNode(Integer.valueOf(menu.getValue()), rootMenu)) {
				// 递归
				menu.setData(getChild(Integer.valueOf(menu.getValue()), rootMenu));
			}
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	// 判断是否是末级节点
	boolean endNode(int id, List<Permission> rootMenu) {
		for (Permission permission : rootMenu) {
			if (permission.getParentId() == id) {
				return true;
			}
		}
		return false;
	}

	// 根据父节点的ID获取所有子节点
	@Override
	public List<Permission> getChildPerms(List<Permission> list, int parentId) {
		// TODO Auto-generated method stub
		List<Permission> returnList = new ArrayList<>();
		for (Iterator<Permission> iterator = list.iterator(); iterator.hasNext();) {
			Permission t = iterator.next();
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	@Override
	public void recursionFn(List<Permission> list, Permission permission) {
		// TODO Auto-generated method stub
		List<Permission> childList = getChildList(list, permission);
		permission.setChildren(childList);
		for (Permission tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				Iterator<Permission> it = childList.iterator();
				while (it.hasNext()) {
					Permission n = it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	// 得到子节点列表
	@Override
	public List<Permission> getChildList(List<Permission> list, Permission permission) {
		// TODO Auto-generated method stub
		List<Permission> permissions = new ArrayList<>();
		Iterator<Permission> it = list.iterator();
		while (it.hasNext()) {
			Permission n = it.next();
			if (n.getParentId().longValue() == permission.getId().longValue()) {
				permissions.add(n);
			}
		}
		return permissions;
	}

	@Override
	public boolean hasChild(List<Permission> list, Permission permission) {
		// TODO Auto-generated method stub
		return getChildList(list, permission).size() > 0 ? true : false;
	}

	// 递归便利Xtree
	public boolean xtree(List<Xtree> xtrees) {
		for (Xtree xtree : xtrees) {
			if (xtree.getData() != new Xtree()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public List<Permission> getAllPers() {
		// TODO Auto-generated method stub
		return permissionMapper.selectAll();
	}

	@Override
	public void deletePersById(Long id) {
		// TODO Auto-generated method stub
		permissionMapper.deleteByPrimaryKey(id);
		permissionMapper.deletePersRoleByPersId(id);

	}

	@Override
	public Permission getPersById(Long id) {
		// TODO Auto-generated method stub
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void createPer(Permission permission) {
		// TODO Auto-generated method stub
		permissionMapper.insert(permission);
	}

	@Override
	public void editPer(Permission permission) {
		// TODO Auto-generated method stub
		permissionMapper.updateByPrimaryKey(permission);
	}

	
}
