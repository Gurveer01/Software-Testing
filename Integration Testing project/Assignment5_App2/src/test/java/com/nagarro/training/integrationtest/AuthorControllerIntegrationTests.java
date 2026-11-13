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
import com.nagarro.training.repository.AuthorRepository;
import com.nagarro.training.response.ErrorResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthorControllerIntegrationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost:";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private AuthorRepository repository;

	@BeforeEach
	public void setup() {
		baseUrl = baseUrl.concat(port + "").concat("/authors");
	}

	@Test
	@Sql(statements = { "INSERT INTO author(id, name) VALUES(1, 'Author 1')",
			"INSERT INTO author(id, name) VALUES(2, 'Author 2')" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = { "DELETE FROM author WHERE id=1",
			"DELETE FROM author WHERE id=2" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getAllAuthors_ShouldReturnAuthorList() {

		ResponseEntity<Author[]> response = testRestTemplate.getForEntity(baseUrl, Author[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().length);
		assertEquals("Author 1", response.getBody()[0].getName());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM author WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/get/" + authorId);

		ResponseEntity<Author> response = testRestTemplate.getForEntity(baseUrl, Author.class);
		Author author = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, author.getId());
		assertEquals("Author 1", author.getName());
	}

	@Test
	public void getAuthorById_ShouldReturnError_WhenAuthorDoesNotExist() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/get/" + authorId);

		ResponseEntity<ErrorResponse> response = testRestTemplate.getForEntity(baseUrl, ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Sql(statements = "DELETE FROM author WHERE name='Author 1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createAuthor_ShouldSaveAndReturnAuthor_WhenAuthorDoesNotExist() {

		Author newAuthor = Author.builder().id(1).name("Author 1").build();

		ResponseEntity<Author> response = testRestTemplate.postForEntity(baseUrl, newAuthor, Author.class);
		Author savedAuthor = response.getBody();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(newAuthor.getName(), savedAuthor.getName());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM author WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createAuthor_ShouldReturnError_WhenAuthorAlreadyExists() {

		Author newAuthor = Author.builder().id(1).name("Author 1").build();

		ResponseEntity<ErrorResponse> response = testRestTemplate.postForEntity(baseUrl, newAuthor,
				ErrorResponse.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM author WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void updateAuthor_ShouldUpdateAndReturnAuthor_WhenAuthorExists() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/" + authorId);
		Author author = Author.builder().id(1).name("Author updated").build();
		HttpEntity<Author> requestEntity = new HttpEntity<Author>(author);

		ResponseEntity<Author> response = testRestTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity,
				Author.class);
		Author updatedAuthor = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(author.getName(), updatedAuthor.getName());
	}

	@Test
	public void updateAuthor_ShouldReturnError_WhenAuthorDoesNotExist() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/" + authorId);
		Author author = Author.builder().id(1).name("Author updated").build();
		HttpEntity<Author> requestEntity = new HttpEntity<Author>(author);

		ResponseEntity<ErrorResponse> response = testRestTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity,
				ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Sql(statements = "INSERT INTO author(id, name) VALUES(1, 'Author 1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void deleteAuthorById_ShouldDeleteAuthor_WhenAuthorExists() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/" + authorId);

		ResponseEntity<Void> response = testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(repository.findById(1)).isEmpty();
	}

	@Test
	public void deleteAuthorById_ShouldReturnError_WhenAuthorDoesNotExist() {

		int authorId = 1;
		baseUrl = baseUrl.concat("/" + authorId);

		ResponseEntity<ErrorResponse> response = testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null,
				ErrorResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
