package com.zhangyao.controller.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.app.PaperType;
import com.zhangyao.entity.system.PageBean;
import com.zhangyao.service.app.PaperService;

@Controller
@RequestMapping("/paper")
public class PaperController {

	@Autowired
	private PaperService paperService;

	@GetMapping("/paperList")
	public String paperLsit(Model model) {
		List<PaperType> types = paperService.getAllTypes();
		model.addAttribute("types", types);
		return "paper/paperlist";
	}

	@GetMapping("/test")
	@ResponseBody
	public List<PaperType> test(Model model) {
		List<PaperType> types = paperService.getAllTypes();
		return types;
	}

	@GetMapping("/findPaper")
	@ResponseBody
	public PageBean findPaper(@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "typename", required = false) String typename,
			@RequestParam(value = "page", required = false) int pageCode,
			@RequestParam(value = "limit", required = false) int pageSize) {
		System.out.println("t:"+title+"a"+author+"ty"+typename);
		return paperService.paperList(author,title,typename,pageCode, pageSize);
	}
}
