package com.nagarro.training.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.repository.AuthorRepository;
import com.nagarro.training.repository.BookRepository;
import com.nagarro.training.response.ErrorResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerIntegrationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost:";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@BeforeEach
	public void setup() {
		baseUrl = baseUrl.concat(port + "").concat("/books");
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO book(id, date, name, author_id) VALUES(1, '25 June 2024', 'Book 1', 1)",
			"INSERT INTO book(id, date, name, author_id) VALUES(2, '25 June 2024', 'Book 2', 1)" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM book WHERE id=1", "DELETE FROM book WHERE id=2",
			"DELETE FROM author WHERE id=1" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getAllBooks_ShouldReturnBookList() {

		baseUrl = baseUrl.concat("/get");

		ResponseEntity<Book[]> response = testRestTemplate.getForEntity(baseUrl, Book[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().length);
		assertEquals("Book 1", response.getBody()[0].getName());
		assertEquals("Book 2", response.getBody()[1].getName());
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO book(id, date, name, author_id) VALUES(1, '25 June 2024', 'Book 1', 1)" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM book WHERE id=1",
			"DELETE FROM author WHERE id=1" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getBookById_ShouldReturnBook_WhenBookExists() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/get/" + bookId);

		ResponseEntity<Book> response = testRestTemplate.getForEntity(baseUrl, Book.class);
		Book book = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, book.getId());
		assertEquals("Book 1", book.getName());
	}

	@Test
	public void getBookById_ShouldReturnError_WhenBookDoesNotExist() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/get/" + bookId);

		ResponseEntity<ErrorResponse> response = testRestTemplate.getForEntity(baseUrl, ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM book WHERE id=1",
			"DELETE FROM author WHERE id=1" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createBook_ShouldSaveAndReturnBook_WhenBookDoesNotExist() {

		baseUrl = baseUrl.concat("/add");
		Author author = authorRepository.findById(1).get();
		Book newBook = Book.builder().id(1).name("Book 1").date("25 June 2024").author(author).build();

		ResponseEntity<Book> response = testRestTemplate.postForEntity(baseUrl, newBook, Book.class);
		Book savedBook = response.getBody();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(newBook.getName(), savedBook.getName());
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO book(id, date, name, author_id) VALUES(1, '25 June 2024', 'Book 1', 1)" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM book WHERE id=1",
			"DELETE FROM author WHERE id=1" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createBook_ShouldReturnError_WhenBookAlreadyExists() {

		baseUrl = baseUrl.concat("/add");
		Author author = authorRepository.findById(1).get();
		Book newBook = Book.builder().id(1).name("Book 1").date("25 June 2024").author(author).build();

		ResponseEntity<ErrorResponse> response = testRestTemplate.postForEntity(baseUrl, newBook, ErrorResponse.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO book(id, date, name, author_id) VALUES(1, '25 June 2024', 'Book 1', 1)" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM book WHERE id=1",
			"DELETE FROM author WHERE id=1" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void updateBook_ShouldUpdateAndReturnBook_WhenBookExists() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/update/" + bookId);
		Author author = authorRepository.findById(1).get();
		Book book = Book.builder().id(1).name("Book updated").date("25 June 2024").author(author).build();
		HttpEntity<Book> requestEntity = new HttpEntity<Book>(book);

		ResponseEntity<Book> response = testRestTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Book.class);
		Book updatedBook = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(book.getName(), updatedBook.getName());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM author WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void updateBook_ShouldReturnError_WhenBookDoesNotExist() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/update/" + bookId);
		Author author = authorRepository.findById(1).get();
		Book book = Book.builder().id(1).name("Book updated").date("25 June 2024").author(author).build();
		HttpEntity<Book> requestEntity = new HttpEntity<Book>(book);

		ResponseEntity<ErrorResponse> response = testRestTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity,
				ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO book(id, date, name, author_id) VALUES(1, '25 June 2024', 'Book 1', 1)" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM author WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void deleteBookById_ShouldDeleteBook_WhenBookExists() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/delete/" + bookId);

		ResponseEntity<Void> response = testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(bookRepository.findById(1)).isEmpty();
	}

	@Test
	public void deleteAuthorById_ShouldReturnError_WhenBookDoesNotExist() {

		int bookId = 1;
		baseUrl = baseUrl.concat("/delete/" + bookId);

		ResponseEntity<ErrorResponse> response = testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null,
				ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
