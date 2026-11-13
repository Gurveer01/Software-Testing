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
import com.nagarro.training.model.Author;
import com.nagarro.training.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService service;

	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors() {
		List<Author> list = service.getAllAuthors();

		return new ResponseEntity<List<Author>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id) 
    		throws RecordNotFoundException {
		
		Author entity = service.getAuthorById(id);
 
        return new ResponseEntity<Author>(entity, new HttpHeaders(), HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) 
    		throws RecordNotFoundException {
		
		Author created = service.createAuthor(author);
        return new ResponseEntity<Author>(created, new HttpHeaders(), HttpStatus.CREATED);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable("id") Integer id, @RequestBody Author author) 
			throws RecordNotFoundException {
		Author updated = service.updateAuthorById(id, author);
		return new ResponseEntity<Author>(updated, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
    public HttpStatus deleteAuthorById(@PathVariable("id") Integer id) 
    		throws RecordNotFoundException {
		
        service.deleteAuthorById(id);
        return HttpStatus.NO_CONTENT;
    }
	
}
