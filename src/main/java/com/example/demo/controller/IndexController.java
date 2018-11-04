package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String toPageIndex() {
		return "index";
	}
	
	@RequestMapping("/qa")
	public String toQa() {
		return "qa";
	}
	
	@RequestMapping("/Qalist")
	public String toQaList() {
		return "QaList";
	}
	
	@RequestMapping("/QaDetail")
	public String toQaDetail() {
		return "QaDetail";
	}
	
	@RequestMapping("/QaDetail1")
	public String toQaDetail1() {
		return "QaDetail1";
	}
	
	@RequestMapping("/QaDetail2")
	public String toQaDetail2() {
		return "QaDetail2";
	}
	
	@RequestMapping({"/cover","/"})
	public String toCover() {
		return "cover";
	}
	
	@RequestMapping("/login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/about")
	public String toAbout() {
		return "about";
	}
	@RequestMapping("/QaDetail3")
	public String toQaDetail3() {
		return "QaDetail3";
	}
	@RequestMapping("/QaDetail4")
	public String toQaDetail4() {
		return "QaDetail4";
	}

}
