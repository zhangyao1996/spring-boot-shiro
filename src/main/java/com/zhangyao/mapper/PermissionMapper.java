package com.zhangyao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhangyao.entity.system.Permission;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:18:23 PM
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

	List<Permission> selectPermissionByUserId(@Param("userId") long userId);

	List<Permission> selectPermissionByRoleId(@Param("roleId") long roleId);

	// 删除sys_per_role
	@Delete({ "delete from sys_role_permission where permission_id=#{id}" })
	void deletePersRoleByPersId(Long id);
}
