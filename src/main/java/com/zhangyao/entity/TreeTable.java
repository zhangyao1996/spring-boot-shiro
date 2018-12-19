package com.zhangyao.entity;

import java.util.List;

import lombok.Data;

@Data
public class TreeTable {
	String msg;
	
	Integer code;
	
	List<Permission> data;
	
	Integer count;
	
}
