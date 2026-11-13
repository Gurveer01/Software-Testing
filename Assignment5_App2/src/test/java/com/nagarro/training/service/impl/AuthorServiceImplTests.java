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
import com.nagarro.training.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTests {
	
	@Mock
	private AuthorRepository repository;
	
	@InjectMocks
	private AuthorServiceImpl service;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getAllAuthors_ShouldReturnAuthorList_WhenAuthorsExist() {
		
		Author author1 = Author.builder().name("Robert").build();
		Author author2 = Author.builder().name("Williams").build();
		List<Author> auhtors = Arrays.asList(author1, author2);
		
		when(repository.findAll()).thenReturn(auhtors);
		
		List<Author> result = service.getAllAuthors();
		
		assertEquals(2, result.size());
		assertEquals("Robert", result.get(0).getName());
		verify(repository, times(1)).findAll();
		
	}
	
	@Test
	public void getAllAuthors_ShouldReturnEmptyList_WhenNoAuthorExist() {
		
		when(repository.findAll()).thenReturn(new ArrayList<>());
		
		List<Author> result = service.getAllAuthors();
		
		assertTrue(result.isEmpty());
		verify(repository, times(1)).findAll();
		
	}
	
	@Test
	public void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() throws RecordNotFoundException{
		
		Author author = Author.builder().id(1).name("Robert").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(author));
		
		Author result = service.getAuthorById(1);
		
		assertEquals("Robert", result.getName());
		verify(repository, times(1)).findById(1);
	}
	
	@Test
	public void getAuthorById_ShouldThrowException_WhenAuthorDoesNotExist() {
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.getAuthorById(1));
		verify(repository, times(1)).findById(1);
	}
	
	@Test
	public void getAuthorByName_ShouldReturnAuthor_WhenAuthorExists() throws RecordNotFoundException{
		
		Author author = Author.builder().id(1).name("Robert").build();
		
		when(repository.findByName(anyString())).thenReturn(Optional.of(author));
		
		Author result = service.getAuthorByName("Robert");
		
		assertEquals("Robert", result.getName());
		verify(repository, times(1)).findByName("Robert");
	}
	
	@Test
	public void getAuthorByName_ShouldThrowException_WhenAuthorDoesNotExist() {
		
		when(repository.findByName(anyString())).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.getAuthorByName("Robert"));
		verify(repository, times(1)).findByName("Robert");
		
	}
	
	@Test
	public void createAuthor_ShouldSaveAndReturnAuthor_WhenAuthorDoesNotExist() throws RecordAlreadyExistsException{
		
		Author author = Author.builder().id(1).name("Robert").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		when(repository.save(any(Author.class))).thenReturn(author);
		
		Author result = service.createAuthor(author);
		
		assertEquals("Robert", result.getName());
		verify(repository, times(1)).findById(author.getId());
		verify(repository, times(1)).save(author);
		
	}
	
	@Test
	public void createAuthor_ShouldThrowException_WhenAuthorExists() {
		
		Author author = Author.builder().id(1).name("Robert").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(author));
		
		assertThrows(RecordAlreadyExistsException.class, () -> service.createAuthor(author));
		verify(repository, times(1)).findById(author.getId());
		
	}
	
	@Test
	public void updateAuthorById_ShouldUpdateAndReturnAuthor_WhenAuthorExists() throws RecordNotFoundException{
		
		Author existingAuthor = Author.builder().id(1).name("Robert").build();
		Author updatedAuthor = Author.builder().id(1).name("Sam").build();
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(existingAuthor));
		when(repository.save(any(Author.class))).thenReturn(updatedAuthor);
		
		Author result = service.updateAuthorById(1, updatedAuthor);
		
		assertEquals("Sam", result.getName());
		verify(repository, times(1)).findById(updatedAuthor.getId());
		verify(repository, times(1)).save(existingAuthor);
		
	}
	
	@Test
	public void updateAuthorById_ShouldThrowException_WhenAuthorDoesNotExist() {
		
		Author updatedAuthor = Author.builder().id(1).name("Sam").build();
		
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.updateAuthorById(1, updatedAuthor));
		verify(repository, times(1)).findById(updatedAuthor.getId());
		
	}
	
	@Test
	public void deleteAuthorById_ShouldDeleteAuthor_WhenAuthorExists() throws RecordNotFoundException{
		
		Author author = Author.builder().id(1).name("Robert").build();
		
		when(repository.findById(1)).thenReturn(Optional.of(author));
		
		service.deleteAuthorById(1);
		
		verify(repository, times(1)).findById(1);
		verify(repository, times(1)).deleteById(1);
		
	}
	
	@Test
	public void deleteAuthorById_ShouldThrowException_WhenAuthorDoesNotExist() {
		
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		assertThrows(RecordNotFoundException.class, () -> service.deleteAuthorById(1));
		verify(repository, times(1)).findById(1);
		
	}
}
