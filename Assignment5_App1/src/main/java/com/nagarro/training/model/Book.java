package com.nagarro.training.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gurveersingh
 *
 */

public class Book {
	
	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("author")
	private Author author;
	
	@JsonProperty("date")
	private String date;

	public Book() {
		super();
	}

	public Book(Integer id, String name, Author author, String date) {
		super();
		this.id = id;
		this.name = name;
		this.author=author;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", date=" + date + "]";
	}
}