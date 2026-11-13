package com.nagarro.training.util;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.constants.ApiUrl;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;

@Component
public class ServiceUtil {
	
	private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ServiceUtil(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Book> getAllBooks() {
    	
    	String url = ApiUrl.GET_BOOKS;
        URI uri = URI.create(url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        List<Book> books = new ArrayList<>();
        try {
            books = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<Book>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
    
    public List<Author> getAuthors() {
    	
        String url = ApiUrl.GET_AUTHORS;
        URI uri = URI.create(url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        List<Author> authors = new ArrayList<>();
        try {
            authors = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<Author>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authors;
    }

    public Author getAuthor(String id) {
    	
        String url = ApiUrl.GET_AUTHOR_BY_ID + id;
        URI uri = URI.create(url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        Author author = new Author();
        try {
            author = objectMapper.readValue(responseEntity.getBody(), Author.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return author;
    }

    public String addBook(String jsonRequest) {
    	
    	String url = ApiUrl.ADD_BOOK;
        URI uri = URI.create(url);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);

        return responseEntity.getBody();
    }

    public HttpStatus deleteBook(String id) {
    	
        String url = ApiUrl.DELETE_BOOK + id;
        URI uri = URI.create(url);

        restTemplate.delete(uri);

        return HttpStatus.NO_CONTENT;
    }

    public Book getBook(String id) {
    	
        String url = ApiUrl.GET_BOOK_BY_ID + id;
        URI uri = URI.create(url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        Book book = new Book();
        try {
            book = objectMapper.readValue(responseEntity.getBody(), Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return book;
    }

    public String updateBook(String id, String jsonRequest) {
    	
        String url = ApiUrl.UPDATE_BOOK + id;
        URI uri = URI.create(url);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

        restTemplate.put(uri, requestEntity);

        return "Book updated successfully";
    }
}
