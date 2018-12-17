package com.zhangyao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.zhangyao.entity.User;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 3:17:37 PM
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	User selectUserByName(String userName);

	Page<User> findByPage(@Param("userName") String username);
}
