package com.zhangyao.controller.app;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.app.Paper;
import com.zhangyao.entity.app.PaperType;
import com.zhangyao.entity.system.PageBean;
import com.zhangyao.entity.system.Result;
import com.zhangyao.service.app.PaperService;

@Controller
@RequestMapping("/paper")
public class PaperController {

	@Autowired
	private PaperService paperService;

	// 文章列表
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
		// System.out.println("t:"+title+"a"+author+"ty"+typename);
		return paperService.paperList(author, title, typename, pageCode, pageSize);
	}

	@RequestMapping("/add")
	public String add(Model model) {
		List<PaperType> types = paperService.getAllTypes();
		model.addAttribute("types", types);
		return "paper/add";
	}

	@PostMapping("/add")
	@ResponseBody
	public Result addPaper(@RequestBody(required = false) Paper paper) {

		Long time = System.currentTimeMillis();
		paper.setUploadTime(new Date(time));
		System.out.println(paper);
		try {
			paperService.createPaper(paper);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new Result(false, "添加失败");
		}

	}

	@GetMapping("/edit")
	public String edit(Long id, Model model) {

		Paper paper = paperService.getPaperById(id);
		List<PaperType> types = paperService.getAllTypes();
		model.addAttribute("types", types);
		model.addAttribute("paper", paper);
		return "paper/edit";
	}

	@DeleteMapping("/del")
	@ResponseBody
	public Result delPaper(Long id) {

		try {
			paperService.deletePaperById(id);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new Result(false, "删除失败");
		}
	}

	@DeleteMapping("/deletePaperIds")
	@ResponseBody
	public Result delPaperIds(String ids) {
		String[] id = ids.split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				Long num = Long.valueOf(id[i]);
				paperService.deletePaperById(num);
			}
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new Result(false, "删除失败");
		}
	}

	@PostMapping("/edit")
	@ResponseBody
	public Result editPaper(@RequestBody(required = false) Paper paper) {

		Long time = System.currentTimeMillis();
		paper.setUploadTime(new Date(time));
		System.out.println(paper);
		try {
			paperService.editPaper(paper);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new Result(false, "修改失败");
		}

	}

	// 文章分类管理
	@GetMapping("/paperAssortment")
	public String paperAssortment() {
		System.out.println("types");
		return "paper/typelist";
	}

	@RequestMapping("/findType")
	@ResponseBody
	public PageBean findAllType(@RequestParam("page") Integer pageCode, @RequestParam("limit") Integer pageSize) {
		return paperService.findAllTypes(pageCode, pageSize);
	}

	@GetMapping("/addType")
	public String addType() {
		return "paper/addtype";
	}

	@PostMapping("/addType")
	@ResponseBody
	public Result addType(@RequestBody(required = false) PaperType paperType) {
		try {
			paperService.addPaperType(paperType);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new Result(false, "添加失败");
		}
	}

	@GetMapping("/editType")
	public String editType(Integer id, Model model) {
		PaperType type = paperService.getPaperTypeById(id);
		model.addAttribute("type", type);
		System.out.println("type:" + type);
		return "paper/edittype";
	}

	@PostMapping("/editType")
	@ResponseBody
	public Result editType(@RequestBody(required = false) PaperType paperType) {
		try {
			paperService.editPaperType(paperType);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("yic:" + e.getMessage());
			return new Result(false, "修改失败");
		}
	}

	@DeleteMapping("/delType")
	@ResponseBody
	public Result deleteType(Integer id) {
		try {
			paperService.deleteType(id);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("yic:" + e.getMessage());
			return new Result(false, "删除失败");
		}
	}
}
