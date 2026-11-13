package com.nagarro.training.constants;

public class ApiUrl {

	public static final String URL = "http://localhost:8082";

	public static final String URL_BOOKS = URL + "/books";

	public static final String GET_BOOKS = URL_BOOKS + "/get";

	public static final String GET_BOOK_BY_ID = URL_BOOKS + "/get/";

	public static final String ADD_BOOK = URL_BOOKS + "/add";

	public static final String UPDATE_BOOK = URL_BOOKS + "/update/";

	public static final String DELETE_BOOK = URL_BOOKS + "/delete/";
	
	public static final String URL_AUTHORS = URL + "/authors";

	public static final String GET_AUTHORS = URL_AUTHORS;

	public static final String GET_AUTHOR_BY_ID = URL_AUTHORS + "/get/";

	public static final String ADD_AUTHOR = URL_AUTHORS + "/add";

	public static final String UPDATE_AUTHOR = URL_AUTHORS + "/update/";

	public static final String DELETE_AUTHOR = URL_AUTHORS + "/delete/";
	
}
