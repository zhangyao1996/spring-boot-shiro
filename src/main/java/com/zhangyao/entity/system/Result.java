package com.zhangyao.entity.system;

import lombok.Data;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 7, 2018 9:13:16 AM
 */
@Data
public class Result {

	private boolean result;
	private String msg;

	public Result(boolean result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}

}
