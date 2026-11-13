package com.nagarro.training.service;

import java.util.List;

import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Book;

/**
 * @author gurveersingh
 *
 */

public interface BookService {
	
	public List<Book> getAllBooks();

	public Book getBookById(Integer id) 
			throws RecordNotFoundException;
	
	public Book createBook(Book entity) 
			throws RecordAlreadyExistsException;
	
	public Book updateBookById(Integer id, Book entity) 
			throws RecordNotFoundException;

	public Book createOrUpdateBook(Book entity) 
			throws RecordNotFoundException;

	public void deleteBookById(Integer id) 
			throws RecordNotFoundException;
}
