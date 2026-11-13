package com.nagarro.training.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Book;
import com.nagarro.training.repository.BookRepository;
import com.nagarro.training.service.BookService;

/**
 * @author gurveersingh
 *
 */

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public List<Book> getAllBooks() {

		// returns list of all books
		List<Book> bookList = bookRepo.findAll();

		if (bookList.size() > 0) {
			return bookList;
		} 
		else {
			return new ArrayList<Book>();
		}
	}

	@Override
	public Book getBookById(Integer id) throws RecordNotFoundException {

		// returns book entity or null value
		Optional<Book> book = bookRepo.findById(id);

		// checks if book is present or not
		if (book.isPresent()) {
			return book.get();
		} 
		else {
			throw new RecordNotFoundException("No book exist for given id", id);
		}
	}

	@Override
	public Book createBook(Book entity) throws RecordAlreadyExistsException {

		// returns book entity or null value
		Optional<Book> book = bookRepo.findById(entity.getId());

		// checks if author is present in database
		if (book.isPresent()) {

			throw new RecordAlreadyExistsException("Book already exists for given id", book.get().getId());
		} 
		else {
			
//			Author author=entity.getAuthor(); 
//			
//			if(author.getBookList()==null) {
//				List<Book> newList = new ArrayList<>();
//				newList.add(entity);
//				author.setBookList(newList);
//			}
//			else {
//				List<Book> bookList = author.getBookList();
//				bookList.add(entity); 
//				author.setBookList(bookList);
//			}		
			
			//saves author entity in database
			//authorRepo.save(author);
			
			bookRepo.save(entity);
			return entity;
		}
	}
	
	@Override
	public Book updateBookById(Integer id, Book entity) 
			throws RecordNotFoundException {
		
		// returns book entity or null value
		Optional<Book> book = bookRepo.findById(id);
		
		// checks if book is present or not
		if (book.isPresent()) {

			// gets book entity from database
			Book newEntity = book.get();

			// updates book entity fields
			newEntity.setName(entity.getName());
			newEntity.setAuthor(entity.getAuthor());
			newEntity.setDate(entity.getDate());
		
			// saves book entity in database
			newEntity = bookRepo.save(newEntity);
			
			return newEntity;
		}
		else {
			throw new RecordNotFoundException("No book exist for given id", id);
		}
		
	}

	@Override
	public Book createOrUpdateBook(Book entity) throws RecordNotFoundException {

		// checks if id is not null
		if (entity.getId() != null) {

			// returns book entity or null value
			Optional<Book> book = bookRepo.findById(entity.getId());

			// checks if book is present or not
			if (book.isPresent()) {

				// gets book entity from database
				Book newEntity = book.get();

				// updates book entity fields
				newEntity.setName(entity.getName());
				newEntity.setAuthor(entity.getAuthor());
				newEntity.setDate(entity.getDate());

				// updates book field in author entity
				
				/*
				 * Author author=entity.getAuthor(); List<Book> bookList = author.getBookList();
				 * bookList.add(newEntity); author.setBookList(bookList);
				 */
				 

				// saves book entity in database
				newEntity = bookRepo.save(newEntity);

				// saves author entity in database
				// authorRepo.save(author);

				return newEntity;
			} 
			else {
				entity = bookRepo.save(entity);

				return entity;
			}
		}

		else {
			entity = bookRepo.save(entity);
			return entity;
		}
	}

	@Override
	public void deleteBookById(Integer id) throws RecordNotFoundException {

		// returns book entity or null value
		Optional<Book> book = bookRepo.findById(id);

		// checks if book is present or not
		if (book.isPresent()) {

			// deleting book
			bookRepo.deleteById(id);
		} 
		else {
			throw new RecordNotFoundException("No book exist for given id", id);
		}
	}

}
