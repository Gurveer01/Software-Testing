package com.nagarro.training.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonIgnore()
	private List<Book> bookList;

	public Author() {
		super();
	}

	public Author(int id, String name, List<Book> bookList) {
		super();
		this.id = id;
		this.name = name;
		this.bookList = bookList;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", bookList=" + bookList + "]";
	}
	
}
