package com.nagarro.training.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.training.constants.ApiUrl;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ApiUtil;
import com.nagarro.training.util.ServiceUtil;

@Controller
public class DashboardController {
	
	@Autowired
	private ServiceUtil serviceUtil;

	@PostMapping("/back")
	public ModelAndView backDashboard() {
		ModelAndView mv = new ModelAndView();
		List<Book> bookList = serviceUtil.getAllBooks();
		mv.addObject("bookList", bookList);
		mv.setViewName("dashboard");
		return mv;
	}

	@PostMapping("/delete")
	public ModelAndView deleteBook(HttpServletRequest request) {
		String bookId = request.getParameter("id");
		HttpStatus status= serviceUtil.deleteBook(bookId);
		ModelAndView mv = new ModelAndView();
		List<Book> bookList = serviceUtil.getAllBooks();
		mv.addObject("bookList", bookList);
		mv.setViewName("dashboard");
		return mv;
	}
}
