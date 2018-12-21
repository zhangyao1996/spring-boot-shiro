package com.zhangyao.service.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public PageBean paperList(String author, String title, String typename, Integer pageCode, Integer pageSize) {
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

	@Override
	public void createPaper(Paper paper) {
		// TODO Auto-generated method stub
		paperMaper.insert(paper);
	}

	@Override
	public Paper getPaperById(Long id) {
		// TODO Auto-generated method stub
		return paperMaper.selectByPrimaryKey(id);
	}

	@Override
	public void editPaper(Paper paper) {
		// TODO Auto-generated method stub
		paperMaper.updateByPrimaryKey(paper);
	}

	@Override
	public void deletePaperById(Long id) {
		// TODO Auto-generated method stub
		paperMaper.deleteByPrimaryKey(id);
	}

	@Override
	public PageBean findAllTypes(Integer pageCode, Integer pageSize) {
		// TODO Auto-generated method stub
		// 使用MyBatis 分页插件
		PageHelper.startPage(pageCode, pageSize);
		// 查询所有数据
		Page<PaperType> page =  (Page<PaperType>) paperMaper.findAllPaperType();
		return new PageBean(page.getResult(), 0, "成功", page.getTotal());
	}

	@Override
	public void addPaperType(PaperType paperType) {
		// TODO Auto-generated method stub
		paperMaper.addPaperType(paperType);
	}

	@Override
	public PaperType getPaperTypeById(Integer id) {
		// TODO Auto-generated method stub
		return paperMaper.getPaperTypeById(id);
	}

	@Override
	public void editPaperType(PaperType paperType) {
		// TODO Auto-generated method stub
		paperMaper.editPaperType(paperType);
	}
	
	@Override
	//事务回滚
	@Transactional
	 public void deleteType(Integer id) {
		paperMaper.deleteType(id);
		paperMaper.deletePaperByTypeId(id);
	}

	
}
