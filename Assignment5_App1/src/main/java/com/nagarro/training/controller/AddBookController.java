package com.nagarro.training.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ServiceUtil;


@Controller
public class AddBookController {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/addView")
	public ModelAndView getAddView() {
		
		List<Author> authors= serviceUtil.getAuthors();
		ModelAndView mv = new ModelAndView();
		mv.addObject("authors", authors);
		
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
		mv.addObject("date", formattedDate);
		
		mv.setViewName("addbook");
		return mv;
	}
	
	@PostMapping("/addBook")
	public ModelAndView addBook(HttpServletRequest request) {
		Book book = new Book();
		
		book.setId(Integer.parseInt(request.getParameter("bookCode")));
		book.setName(request.getParameter("bookName"));
		String authorId=request.getParameter("authorId");
		Author author = serviceUtil.getAuthor(authorId);
		book.setAuthor(author);
		book.setDate(request.getParameter("date"));
		
		String jsonRequest = "";
		
		try {
			jsonRequest = objectMapper.writeValueAsString(book);
			System.out.println(jsonRequest);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = serviceUtil.addBook(jsonRequest);
		
		ModelAndView mv = new ModelAndView();
		List<Book> bookList= serviceUtil.getAllBooks();
		mv.addObject("bookList", bookList);
		mv.setViewName("dashboard");
		return mv;
	}
}
