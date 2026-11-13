package com.nagarro.training.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.service.BookService;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BookService bookService;
	
	private Author author;
	private Book book;
	private Integer bookId;
	
	@BeforeEach
	public void setup() {
		author = Author.builder().id(1).name("Robert").bookList(null).build();
		book = Book.builder().id(2).name("Book1").author(author).date("14 June 2024").build();
		bookId = book.getId();
	}
	
	@Test
	public void getAllBooks_ShouldReturnBookList_WhenBooksExist() throws Exception{
		
		List<Book> books = Arrays.asList(book);
		
		when(bookService.getAllBooks()).thenReturn(books);
		
		mockMvc.perform(get("/books/get"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(book.getId()))
				.andExpect(jsonPath("$[0].name").value(book.getName()))
				.andExpect(jsonPath("$[0].author.name").value(book.getAuthor().getName()))
				.andExpect(jsonPath("$[0].date").value(book.getDate()));
		
		verify(bookService, times(1)).getAllBooks();
	}
	
	@Test
	public void getBookById_ShouldReturnBook_WhenBookExists() throws Exception{
		
		when(bookService.getBookById(anyInt())).thenReturn(book);
		
		mockMvc.perform(get("/books/get/{id}", bookId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(book.getId()))
				.andExpect(jsonPath("$.name").value(book.getName()))
				.andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()))
				.andExpect(jsonPath("$.date").value(book.getDate()));
		
		verify(bookService, times(1)).getBookById(anyInt());
				
	}
	
	@Test
	public void getBookById_ShouldThrowException_WhenBookDoesNotExist() throws Exception{
		
		when(bookService.getBookById(anyInt())).thenThrow(new RecordNotFoundException("No book exist for given id", bookId));
		
		mockMvc.perform(get("/books/get/{id}", bookId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No book exist for given id - " + bookId))
	            .andExpect(jsonPath("$.details").exists())
	            .andExpect(jsonPath("$.timestamp").exists());
		
		verify(bookService, times(1)).getBookById(anyInt());
				
	}
	
	@Test
	public void createBook_ShouldSaveAndReturnBook_WhenBookDoesNotExist() throws Exception{
		
		when(bookService.createBook(any(Book.class))).thenReturn(book);
		
		String jsonRequest = objectMapper.writeValueAsString(book);
		
		mockMvc.perform(post("/books/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(book.getId()))
				.andExpect(jsonPath("$.name").value(book.getName()))
				.andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()))
				.andExpect(jsonPath("$.date").value(book.getDate()));
		
		verify(bookService, times(1)).createBook(any(Book.class));
	}
	
	@Test
	public void createBook_ShouldThrowException_WhenBookAlreadyExist() throws Exception{
		
		when(bookService.createBook(any(Book.class))).thenThrow(new RecordAlreadyExistsException("Book already exists for given id", bookId));
		
		String jsonRequest = objectMapper.writeValueAsString(book);
		
		mockMvc.perform(post("/books/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("Book already exists for given id - " + bookId))
				.andExpect(jsonPath("$.details").exists())
				.andExpect(jsonPath("$.timestamp").exists());
		
		verify(bookService, times(1)).createBook(any(Book.class));
	}
	
	@Test
	public void updateBook_ShouldUpdateAndReturnBook_WhenBookExists() throws Exception{
		
		when(bookService.updateBookById(anyInt(), any(Book.class))).thenReturn(book);
		
		String jsonRequest = objectMapper.writeValueAsString(book);
		
		mockMvc.perform(put("/books/update/{id}", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(book.getId()))
				.andExpect(jsonPath("$.name").value(book.getName()))
				.andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()))
				.andExpect(jsonPath("$.date").value(book.getDate()));
		
		verify(bookService, times(1)).updateBookById(anyInt(), any(Book.class));
	}
	
	@Test
	public void updateBook_ShouldThrowException_WhenBookExists() throws Exception{
		
		when(bookService.updateBookById(anyInt(), any(Book.class))).thenThrow(new RecordNotFoundException("No book exist for given id", bookId));
		
		String jsonRequest = objectMapper.writeValueAsString(book);
		
		mockMvc.perform(put("/books/update/{id}", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No book exist for given id - " + bookId))
		        .andExpect(jsonPath("$.details").exists())
		        .andExpect(jsonPath("$.timestamp").exists());
		
		verify(bookService, times(1)).updateBookById(anyInt(), any(Book.class));
	}
	
	@Test
	public void deleteBookById_ShouldDeleteBook_WhenBookExists() throws Exception{
		
		doNothing().when(bookService).deleteBookById(anyInt());
		
		mockMvc.perform(delete("/books/delete/{id}", bookId))
				.andExpect(status().isOk());
		
		verify(bookService, times(1)).deleteBookById(anyInt());
				
	}
	
	@Test
	public void deleteBookById_ShouldThrowException_WhenBookDoesNotExist() throws Exception{
		
		doThrow(new RecordNotFoundException("No book exist for given id", bookId)).when(bookService).deleteBookById(anyInt());
		
		mockMvc.perform(delete("/books/delete/{id}", bookId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No book exist for given id - " + bookId))
	            .andExpect(jsonPath("$.details").exists())
	            .andExpect(jsonPath("$.timestamp").exists());
		
		verify(bookService, times(1)).deleteBookById(anyInt());
				
	}
}
