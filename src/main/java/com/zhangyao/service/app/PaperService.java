package com.zhangyao.service.app;

import java.util.List;

import com.zhangyao.entity.app.PaperType;
import com.zhangyao.entity.system.PageBean;

public interface PaperService {
	
	PageBean paperList(String author, String title, String typename, Integer pageCode,Integer pageSize);
	
	List<PaperType> getAllTypes();
}
