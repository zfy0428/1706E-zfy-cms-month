package com.zfy.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Utils.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zfy.cms.comon.ResultMsg;
import com.zfy.cms.entity.Link;
import com.zfy.cms.entity.Special;
import com.zfy.cms.service.SpecialService;
import com.zfy.cms.utils.PageUtils;

@RequestMapping("special")
@Controller
public class SpecialController {
	@Autowired
	SpecialService specialSer;
	//专辑详情页面跳转
		@RequestMapping("getDetail")
		public String toDetail(HttpServletRequest request,Integer aId){
			Special special =  specialSer.findById(aId);
			request.setAttribute("special", special);
			return "my/specoalData";
		}
		
	//专题列表
	@RequestMapping("list")
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page){
		PageHelper.startPage(page, 5);
		
		List<Special> specialList = specialSer.list();
		
		PageInfo<Special> pageInfo = new PageInfo<Special>(specialList);
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/special/list", 5);
		
		request.setAttribute("page", pageStr);
		
		request.setAttribute("specialList", specialList);
		return "admin/special/list";
	}
	//跳转 添加
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(HttpServletRequest request) {
		return "admin/special/add";
	}
	//添加
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public  ResultMsg add(HttpServletRequest request,Special special) {
		if(StringUtils.isEmpty(special.getDigest())) {
			return new ResultMsg(3, "摘要不能为空", "");
		}
		int result = specialSer.add(special);
		if(result>0) {
			return new ResultMsg(1, "添加成功", "");
		}else {
			return new ResultMsg(2, "添加失败，请与管理员联系", "");
		}
	}
	//跳转修改
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(HttpServletRequest request,Integer id) {
		Special special = specialSer.findById(id);
		request.setAttribute("special", special);
		return "admin/special/update";
	}
	//修改
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public  ResultMsg update(HttpServletRequest request,Special special) {
		int result = specialSer.update(special);
		if(result>0) {
			return new ResultMsg(1, "修改成功", "");
		}else {
			return new ResultMsg(2, "修改失败，请与管理员联系", "");
		}
	}
	
	
	
	@RequestMapping(value="addArticle",method=RequestMethod.POST)
	@ResponseBody
	public  ResultMsg addArticle(HttpServletRequest request,Integer specId,Integer articleId) {
		//向专辑添加文章
		int result = specialSer.addArticle(specId,articleId);
		if(result>0) {
			return new ResultMsg(1, "添加成功", "");
		}else {
			return new ResultMsg(2, "添加失败，请与管理员联系", "");
		}
	}
	//从专辑中移除文章
	@RequestMapping(value="removeArticle",method=RequestMethod.POST)
	@ResponseBody
	public  ResultMsg removeArticle(HttpServletRequest request,Integer specId,Integer articleId) {
		int result = specialSer.removeArticle(specId,articleId);
		if(result>0) {
			return new ResultMsg(1, "移除成功", "");
		}else {
			return new ResultMsg(2, "移除失败，请与管理员联系", "");
		}
	}
	//根据id查询special本身以及里面的所有文章
	@RequestMapping("detail")	
	public String detail(HttpServletRequest request,Integer id) {
		Special special =  specialSer.findById(id);
		request.setAttribute("special", special);
		return "admin/special/detail";
	}
	
	
}
