package com.nagarro.training.service;

import java.util.List;

import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Author;

/**
 * @author gurveersingh
 *
 */

public interface AuthorService {

	public List<Author> getAllAuthors();

	public Author getAuthorById(Integer id) 
			throws RecordNotFoundException;

	public Author getAuthorByName(String name) 
			throws RecordNotFoundException;
	
	public Author createAuthor(Author entity) 
			throws RecordAlreadyExistsException;

	public Author updateAuthorById(Integer id, Author entity) 
			throws RecordNotFoundException;

	public void deleteAuthorById(Integer id) 
			throws RecordNotFoundException;
}
