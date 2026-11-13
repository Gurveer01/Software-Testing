package com.nagarro.training.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ServiceUtil;

@Controller
public class EditBookController {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/editView")
	public ModelAndView getEditView(HttpServletRequest request) {
		
		String bookId=request.getParameter("id");
		Book book= serviceUtil.getBook(bookId);
		ModelAndView mv = new ModelAndView();
		List<Author> authors=serviceUtil.getAuthors();
		mv.addObject("authors", authors);
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
		mv.addObject("date", formattedDate);
		mv.addObject("book", book);
		mv.setViewName("editpage");
		return mv;
	}
	
	@PostMapping("/editBook")
	public ModelAndView editBook(HttpServletRequest request) {
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
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String response=serviceUtil.updateBook(book.getId().toString(), jsonRequest);
		
		ModelAndView mv = new ModelAndView();
		List<Book> bookList= serviceUtil.getAllBooks();
		mv.addObject("bookList", bookList);
		mv.setViewName("dashboard");
		return mv;
	}
}
