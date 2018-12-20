package com.zhangyao.entity.system;

import java.util.List;

import lombok.Data;

@Data
public class TreeTable {
	String msg;
	
	Integer code;
	
	List<Permission> data;
	
	Integer count;
	
}
