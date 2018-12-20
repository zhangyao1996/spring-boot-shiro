package com.zhangyao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.Page;
import com.zhangyao.entity.system.User;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:17:37 PM
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	User selectUserByName(String userName);

	Page<User> findByPage(@Param("userName") String username);

	@Insert({ "insert into sys_user_role(user_id,role_id) values(#{userId},#{roleId})" })
	void createUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	// 注意加号之间的字符串
	@Select({ "select sr.role_name  from sys_role sr LEFT JOIN sys_user_role sur"
			+ " ON sur.role_id=sr.role_id WHERE sur.user_id=#{userId}" })
	String findRoleNameByUserId(@Param("userId") Long userId);

	// 更改roleId
	@Update({ "update sys_user_role set role_id=#{roleId} where user_id=#{userId}" })
	void updateRoleIdByUserId(@Param("roleId") Long roleId, @Param("userId") Long userId);

	// 根据userId删除sys_user_role
	@Delete({ "delete from sys_user_role where user_id=#{userId}" })
	void deleteUserRoleByUserId(Long userId);

}
