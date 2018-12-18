package com.zhangyao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pers")
public class PermissionController {

	@RequestMapping("/persList")
	@ResponseBody
	public String persList() {
		return null;
	}
}
