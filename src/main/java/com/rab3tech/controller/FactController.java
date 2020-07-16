package com.rab3tech.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Here we can use only this annotation
@Controller
public class FactController {
	
	//doGet
	@GetMapping("/auth")
	public String calFact(HttpServletRequest req){
		String num=req.getParameter("num");
		int suma=1;
		for(int x=2;x<=Integer.parseInt(num);x++){
			suma=suma*x;
		}
		req.setAttribute("resulta", suma);
		return "fact";  // /fact.jsp
	}

}
