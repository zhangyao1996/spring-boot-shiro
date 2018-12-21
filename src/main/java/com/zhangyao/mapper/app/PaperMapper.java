package com.zhangyao.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhangyao.entity.app.Paper;
import com.zhangyao.entity.app.PaperType;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

	List<Paper> findByPage(@Param("author") String author, @Param("title") String title,
			@Param("typename") String typename);

	List<PaperType> findAllPaperType();

	@Insert({ "insert into sys_paper_type(id,type_name) values(#{id},#{typeName})" })
	void addPaperType(PaperType paperType);
	
	@Select({"select id,type_name AS typeName from sys_paper_type where id=#{id}"})
	PaperType getPaperTypeById(Integer id);
	
	@Update({"update sys_paper_type set type_name=#{typeName} where id=#{id}"})
	void editPaperType(PaperType paperType);
	
	@Delete({"delete  from sys_paper_type where id=#{id}"})
	void deleteType(Integer id);
	
	@Delete({"delete  from sys_paper where type_id=#{typeId}"})
	void deletePaperByTypeId(Integer typeId);
}
