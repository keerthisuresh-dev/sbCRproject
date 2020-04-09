package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*import java.util.Arrays;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;*/



@Controller
public class MainController {

	 @RequestMapping("/")
	    public String welcome() {
	        return "tabtest";
	        
	 }
	 @RequestMapping("/candidate")
	    public String welcomeIndex() {
	        return "index";
	        
	 }
	 @RequestMapping("/requirement")
	    public String welcomeReq() {
	        return "requirement";
	        
	 }
	/* @GetMapping("{tab}")
	    public String tab(@PathVariable String tab) {
	        if (Arrays.asList("tab1","tab2", "tab3")
	                  .contains(tab)) {
	            return "_" + tab;
	        }

	        return "empty";
	    }*/
}
