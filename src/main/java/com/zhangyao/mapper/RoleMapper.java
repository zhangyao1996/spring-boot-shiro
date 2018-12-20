package com.zhangyao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhangyao.entity.system.Role;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 14, 2018 4:00:19 PM
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	Role selectRoleByName(String roleName);

	@Insert({ "insert into sys_role_permission(role_id,permission_id) values(#{roleId}, #{permissionId})" })
	void addRolePers(@Param("roleId") long roleId, @Param("permissionId") long permissionId);

	@Delete({ "DELETE FROM sys_role_permission WHERE role_id=#{roleId}" })
	void deleteRolePers(long roleId);

}
