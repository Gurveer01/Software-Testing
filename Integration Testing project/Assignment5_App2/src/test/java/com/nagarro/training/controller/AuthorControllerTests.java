package com.nagarro.training.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.nagarro.training.service.AuthorService;

@WebMvcTest(controllers =  AuthorController.class)
public class AuthorControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AuthorService authorService;
	
	private Author author;
	private Integer authorId;
	
	@BeforeEach
	public void setup() {
		author = Author.builder().id(1).name("Robert").bookList(null).build();
		authorId = author.getId();
	}
	
	@Test
	public void getAllAuthors_ShouldReturnAuthorList_WhenAuthorsExist() throws Exception{
		
		List<Author> authors = Arrays.asList(author);
		
		when(authorService.getAllAuthors()).thenReturn(authors);
		
		mockMvc.perform(get("/authors"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(author.getId()))
				.andExpect(jsonPath("$[0].name").value(author.getName()));
		
		verify(authorService, times(1)).getAllAuthors();
	}
	
	@Test
	public void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() throws Exception{
		
		when(authorService.getAuthorById(anyInt())).thenReturn(author);
		
		mockMvc.perform(get("/authors/get/{id}", authorId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(author.getId()))
				.andExpect(jsonPath("$.name").value(author.getName()));
		
		verify(authorService, times(1)).getAuthorById(authorId);
				
	}
	
	@Test
	public void getAuthorById_ShouldThrowException_WhenAuthorDoesNotExist() throws Exception{
		
		when(authorService.getAuthorById(anyInt())).thenThrow(new RecordNotFoundException("No author exist for given id", authorId));
		
		mockMvc.perform(get("/authors/get/{id}", authorId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No author exist for given id - " + authorId))
	            .andExpect(jsonPath("$.details").exists())
	            .andExpect(jsonPath("$.timestamp").exists());
		
		verify(authorService, times(1)).getAuthorById(authorId);
				
	}
	
	@Test
	public void createAuthor_ShouldSaveAndReturnAuthor_WhenAuthorDoesNotExist() throws Exception{
		
		when(authorService.createAuthor(any(Author.class))).thenReturn(author);
		
		String jsonRequest = objectMapper.writeValueAsString(author);
		
		mockMvc.perform(post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(author.getId()))
				.andExpect(jsonPath("$.name").value(author.getName()));
		
		verify(authorService, times(1)).createAuthor(any(Author.class));
	}
	
	@Test
	public void createAuthor_ShouldThrowException_WhenAuthorAlreadyExist() throws Exception{
		
		when(authorService.createAuthor(any(Author.class))).thenThrow(new RecordAlreadyExistsException("Author already exists for given id", authorId));
		
		String jsonRequest = objectMapper.writeValueAsString(author);
		
		mockMvc.perform(post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("Author already exists for given id - " + authorId))
				.andExpect(jsonPath("$.details").exists())
				.andExpect(jsonPath("$.timestamp").exists());
		
		verify(authorService, times(1)).createAuthor(any(Author.class));
	}
	
	@Test
	public void updateAuthor_ShouldUpdateAndReturnAuthor_WhenAuthorExists() throws Exception{
		
		when(authorService.updateAuthorById(anyInt(), any(Author.class))).thenReturn(author);
		
		String jsonRequest = objectMapper.writeValueAsString(author);
		
		mockMvc.perform(put("/authors/{id}", authorId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(author.getId()))
				.andExpect(jsonPath("$.name").value(author.getName()));
		
		verify(authorService, times(1)).updateAuthorById(anyInt(), any(Author.class));
	}
	
	@Test
	public void updateAuthor_ShouldThrowException_WhenAuthorExists() throws Exception{
		
		when(authorService.updateAuthorById(anyInt(), any(Author.class))).thenThrow(new RecordNotFoundException("No author exist for given id", authorId));
		
		String jsonRequest = objectMapper.writeValueAsString(author);
		
		mockMvc.perform(put("/authors/{id}", authorId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No author exist for given id - " + authorId))
		        .andExpect(jsonPath("$.details").exists())
		        .andExpect(jsonPath("$.timestamp").exists());
		
		verify(authorService, times(1)).updateAuthorById(anyInt(), any(Author.class));
	}
	
	@Test
	public void deleteAuthorById_ShouldDeleteAuthor_WhenAuthorExists() throws Exception{
		
		doNothing().when(authorService).deleteAuthorById(anyInt());
		
		mockMvc.perform(delete("/authors/{id}", authorId))
				.andExpect(status().isOk());
		
		verify(authorService, times(1)).deleteAuthorById(anyInt());
				
	}
	
	@Test
	public void deleteAuthorById_ShouldThrowException_WhenAuthorDoesNotExist() throws Exception{
		
		doThrow(new RecordNotFoundException("No author exist for given id", authorId)).when(authorService).deleteAuthorById(anyInt());
		
		mockMvc.perform(delete("/authors/{id}", authorId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No author exist for given id - " + authorId))
	            .andExpect(jsonPath("$.details").exists())
	            .andExpect(jsonPath("$.timestamp").exists());
		
		verify(authorService, times(1)).deleteAuthorById(anyInt());
				
	}

}
