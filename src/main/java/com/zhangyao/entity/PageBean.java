package com.zhangyao.entity;

import java.util.List;

import lombok.Data;

/**
* @author zhangyao:
* @date 创建时间：Dec 10, 2018 9:03:24 AM
*/
@Data
public class PageBean {
	
	List data;//数据
	int code;//页码
	String msg;//信息
	long count;//总条数
	
	public PageBean(List data, int code, String msg, long count) {
		super();
		this.data = data;
		this.code = code;
		this.msg = msg;
		this.count = count;
	}

	
	
}
