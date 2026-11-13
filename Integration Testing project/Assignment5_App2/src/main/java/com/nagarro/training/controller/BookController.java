package com.nagarro.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Book;
import com.nagarro.training.service.BookService;

/**
 * @author gurveersingh
 *
 */

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping("/get")
    public ResponseEntity<List<Book>> getAllBooks() {
		
        List<Book> list = service.getAllBooks();
 
        return new ResponseEntity<List<Book>>(list, new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) 
    		throws RecordNotFoundException {
		
    	Book entity = service.getBookById(id);
 
        return new ResponseEntity<Book>(entity, new HttpHeaders(), HttpStatus.OK);
    }
	
	@PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) 
    		throws Exception {
		
    	Book created = service.createBook(book);
        return new ResponseEntity<Book>(created, new HttpHeaders(), HttpStatus.CREATED);
    }
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Integer id, @RequestBody Book book) 
			throws RecordNotFoundException {
		Book updated = service.updateBookById(id, book);
		return new ResponseEntity<Book>(updated, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
    public HttpStatus deleteBookById(@PathVariable("id") Integer id) 
    		throws RecordNotFoundException {
		
        service.deleteBookById(id);
        return HttpStatus.NO_CONTENT;
    }
}
