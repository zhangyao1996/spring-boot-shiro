package com.zhangyao.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.zhangyao.entity.app.Paper;
import com.zhangyao.entity.app.PaperType;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

	List<Paper> findByPage(@Param("author") String author, @Param("title") String title,
			@Param("typename") String typename);

	List<PaperType> findAllPaperType();
}
