package com.nagarro.training.service.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {
	
	@Mock
	private BookRepository repository;
	
	@InjectMocks
	private BookServiceImpl service;
	
	private Author author;
	private Book book;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		author = Author.builder().id(1).name("Robert").bookList(null).build();
		book = Book.builder().id(1).name("Book1").author(author).date("14 June 2024").build();
	}
	
	@Test
	public void getAllBooks_ShouldReturnBookList_WhenBooksExist() {
		
		when(repository.findAll()).thenReturn(Arrays.asList(book));
		
		List<Book> resultList = service.getAllBooks();
		
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
		verify(repository, times(1)).findAll();
		
	}
	
	@Test
	public void getAllBooks_ShouldReturnEmptyList_WhenNoBookExists() {
		
		when(repository.findAll()).thenReturn(new ArrayList<>());
		
		List<Book> resultList = service.getAllBooks();
		
		assertTrue(resultList.isEmpty());
		
	}
	
	@Test
	public void getBookById_ShouldReturnBook_WhenBookExists() throws RecordNotFoundException{
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(book));
		
		Book foundBook = service.getBookById(1);
		
		assertNotNull(foundBook);
		assertEquals("Book1", foundBook.getName());
		verify(repository, times(1)).findById(1);
		
	}
	
	@Test
	public void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.getBookById(1));
		verify(repository, times(1)).findById(1);
		
	}
	
	@Test
	public void createBook_ShouldSaveAndReturnBook_WhenBookDoesNotExist() throws RecordAlreadyExistsException{
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		when(repository.save(any(Book.class))).thenReturn(book);
		
		Book createdBook = service.createBook(book);
		
		assertNotNull(createdBook);
		assertEquals(book.getId(), createdBook.getId());
		verify(repository, times(1)).findById(book.getId());
		verify(repository, times(1)).save(book);
		
	}
	
	@Test
	public void createBook_ShouldThrowException_WhenBookExists() {
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(book));
		
		assertThrows(RecordAlreadyExistsException.class, () -> service.createBook(book));
		verify(repository, times(1)).findById(book.getId());
		
	}
	
	@Test
	public void updateBookById_ShouldUpdateAndReturnBook_WhenBookExists() throws RecordAlreadyExistsException{
		
		Book book2 = Book.builder().id(1).name("Book2").author(author).date("15 June 2024").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(book));
		when(repository.save(any(Book.class))).thenReturn(book);
		
		Book updatedBook = service.updateBookById(1, book2);
		
		assertNotNull(updatedBook);
		assertEquals(book.getId(), updatedBook.getId());
		verify(repository, times(1)).findById(book.getId());
		verify(repository, times(1)).save(book);
		
	}
	
	@Test
	public void updateBookById_ShouldThrowException_WhenBookDoesNotExist() {
		
		Book book2 = Book.builder().id(1).name("Book2").author(author).date("15 June 2024").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.updateBookById(1, book2));
		verify(repository, times(1)).findById(book.getId());
		
	}
	
	@Test
	public void deleteBookById_ShouldDeleteBook_WhenBookExists() throws RecordNotFoundException{
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(book));
		
		service.deleteBookById(1);
		
		verify(repository,times(1)).findById(1);
		verify(repository,times(1)).deleteById(1);
		
	}
	
	@Test
	public void deleteBookById_ShouldThrowException_WhenBookDoesNotExist() {
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.deleteBookById(1));
		verify(repository,times(1)).findById(1);
		
	}
}
