package com.zhangyao.entity;

import java.util.List;

import lombok.Data;

@Data
public class Xtree {
	private String title;
	private String value;
	private boolean checked;
	private List<Xtree> data;
}
