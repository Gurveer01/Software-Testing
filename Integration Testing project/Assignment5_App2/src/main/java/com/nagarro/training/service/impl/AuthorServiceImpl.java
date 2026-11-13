package com.nagarro.training.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.exception.RecordAlreadyExistsException;
import com.nagarro.training.exception.RecordNotFoundException;
import com.nagarro.training.model.Author;
import com.nagarro.training.repository.AuthorRepository;
import com.nagarro.training.service.AuthorService;

/**
 * @author gurveersingh
 *
 */

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository repository;

	@Override
	public List<Author> getAllAuthors() {

		List<Author> authorList = repository.findAll();

		if (authorList.size() > 0) {
			return authorList;
		} else {
			return new ArrayList<Author>();
		}
	}

	@Override
	public Author getAuthorById(Integer id) throws RecordNotFoundException {

		// returns author entity or null value
		Optional<Author> author = repository.findById(id);

		// checks if author is present in database
		if (author.isPresent()) {
			return author.get();
		} else {
			throw new RecordNotFoundException("No author exists for given id", id);
		}
	}

	@Override
	public Author getAuthorByName(String name) throws RecordNotFoundException {

		Optional<Author> author = repository.findByName(name);

		// checks if author is present in database
		if (author.isPresent()) {
			return author.get();
		} else {
			throw new RecordNotFoundException("No author exists for given name", name);
		}

//		//returns list of all authors
//		List<Author> authorList = repository.findAll();
//		
//		 for(Author author: authorList) {
//			 
//			 //checks if author name matches in list
//			 if(author.getName().equals(name)) {
//				 return author;
//			 }
//		 }
//		 throw new RecordNotFoundException("No author exists for given name", name);
	}

	@Override
	public Author createAuthor(Author entity) throws RecordAlreadyExistsException {

		// returns author entity or null value
		Optional<Author> author = repository.findById(entity.getId());

		// checks if author is present in database
		if (author.isPresent()) {

			throw new RecordAlreadyExistsException("Author already exists for given id", author.get().getId());
		} else {
			Author savedAuthor = repository.save(entity);
			return savedAuthor;
		}
	}

	@Override
	public Author updateAuthorById(Integer id, Author entity) throws RecordNotFoundException {

		// returns author entity or null value
		Optional<Author> author = repository.findById(id);

		// checks if author is present
		if (author.isPresent()) {

			// gets author entity from database
			Author newAuthor = author.get();

			// updating author entity fields
			newAuthor.setName(entity.getName());

			// saving author in database
			repository.save(newAuthor);

			return newAuthor;
		} else {
			throw new RecordNotFoundException("No author exists for given id", id);
		}
	}

	@Override
	public void deleteAuthorById(Integer id) throws RecordNotFoundException {

		// returns author entity or null value
		Optional<Author> author = repository.findById(id);

		// checks if author is present or not
		if (author.isPresent()) {

			// deleting author
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No author exists for given id", id);
		}
	}
}
