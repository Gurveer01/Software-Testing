package com.nagarro.training.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.training.constants.ApiUrl;
import com.nagarro.training.dao.LoginDao;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ApiUtil;
import com.nagarro.training.util.ServiceUtil;

@Controller
public class LoginController {

	@Autowired
	LoginDao loginDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();

		//getting username and password
		String uname = request.getParameter("username");
		String pass = request.getParameter("password");

		//function to check user exists or not
		if (loginDao.checkLogin(uname, pass)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", uname);
			List<Book> bookList= serviceUtil.getAllBooks();
			mv.addObject("bookList", bookList);
			mv.setViewName("dashboard");
		} else {
			mv.setViewName("index");
		}
		return mv;
	}
	
	

}
