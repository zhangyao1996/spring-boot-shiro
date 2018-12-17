package com.zhangyao.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.zhangyao.entity.Role;

import tk.mybatis.mapper.common.BaseMapper;

/**
* @author zhangyao:
* @date 创建时间：Dec 14, 2018 4:00:19 PM
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	

}
