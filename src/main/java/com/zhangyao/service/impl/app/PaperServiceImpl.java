package com.zhangyao.service.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangyao.entity.app.Paper;
import com.zhangyao.entity.app.PaperType;
import com.zhangyao.entity.system.PageBean;
import com.zhangyao.mapper.app.PaperMapper;

import com.zhangyao.service.app.PaperService;

@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private PaperMapper paperMaper;
	

	@Override
	public PageBean paperList(String author, String title, String typename,Integer pageCode, Integer pageSize) {
		// TODO Auto-generated method stub
		// 使用MyBatis 分页插件
		PageHelper.startPage(pageCode, pageSize);
		// 查询所有数据
		Page<Paper> page = (Page<Paper>) paperMaper.findByPage(author, title, typename);
		return new PageBean(page.getResult(), 0, "成功", page.getTotal());
	}

	@Override
	public List<PaperType> getAllTypes() {
		// TODO Auto-generated method stub
		return paperMaper.findAllPaperType();
	}
	

}
