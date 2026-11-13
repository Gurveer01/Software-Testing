package com.nagarro.training.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.nagarro.training.config.TestConfig;
import com.nagarro.training.constants.ApiUrl;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ServiceUtilTests {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private MockRestServiceServer server;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Before
	public void setup() {
		server = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
    public void testGetAllBooks() throws Exception {
        String jsonResponse = "[{\"id\":500,\"name\":\"Design Patterns\",\"author\":{\"id\":6,\"name\":\"Enrich Gamma\"},\"date\":\"21 June 2024\"},{\"id\":501,\"name\":\"Clean Code\",\"author\":{\"id\":4,\"name\":\"Robert Cecil\"},\"date\":\"21 June 2024\"}]";
        server.expect(requestTo(new URI(ApiUrl.GET_BOOKS)))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        List<Book> books = serviceUtil.getAllBooks();
        
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getName()).isEqualTo("Design Patterns");
    }

    @Test
    public void testGetAuthors() throws Exception {
        String jsonResponse = "[{\"id\":1,\"name\":\"Anthony\",\"bookList\":[]},{\"id\":2,\"name\":\"Arthur\",\"bookList\":[]}]";
        server.expect(requestTo(new URI(ApiUrl.GET_AUTHORS)))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        List<Author> authors = serviceUtil.getAuthors();
        
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(2);
        assertThat(authors.get(0).getName()).isEqualTo("Anthony");
    }

    @Test
    public void testGetAuthor() throws Exception {
        String jsonResponse = "{\"id\":1,\"name\":\"Anthony\",\"bookList\":[]}";
        server.expect(requestTo(new URI(ApiUrl.GET_AUTHOR_BY_ID + "1")))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        Author author = serviceUtil.getAuthor("1");
        
        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo("Anthony");
    }

    @Test
    public void testAddBook() throws Exception {
        String jsonResponse = "{\"message\":\"Book added successfully\"}";
        server.expect(requestTo(new URI(ApiUrl.ADD_BOOK)))
                .andExpect(request -> {
                	
                    assertThat(request.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
                    assertThat(request.getHeaders().getAcceptCharset()).contains(StandardCharsets.UTF_8);
                })
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        String result = serviceUtil.addBook("{\"id\":505, \"name\":\"New Book\"}");
        
        assertThat(result).isEqualTo("{\"message\":\"Book added successfully\"}");
    }

    @Test
    public void testDeleteBook() throws Exception {
        server.expect(requestTo(new URI(ApiUrl.DELETE_BOOK + "1")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());

        HttpStatus result = serviceUtil.deleteBook("1");
        
        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetBook() throws Exception {
        String jsonResponse = "{\"id\":500,\"name\":\"Design Patterns\",\"author\":{\"id\":6,\"name\":\"Enrich Gamma\"},\"date\":\"21 June 2024\"}";
        server.expect(requestTo(new URI(ApiUrl.GET_BOOK_BY_ID + "500")))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        Book book = serviceUtil.getBook("500");
        
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualTo("Design Patterns");
    }

    @Test
    public void testUpdateBook() throws Exception {
        server.expect(requestTo(new URI(ApiUrl.UPDATE_BOOK + "500")))
                .andExpect(request -> {
                	
                    assertThat(request.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
                    assertThat(request.getHeaders().getAcceptCharset()).contains(StandardCharsets.UTF_8);
                })
                .andRespond(withSuccess());

        String result = serviceUtil.updateBook("500", "{\"id\":500, \"name\":\"Updated Book\"}");
        
        assertThat(result).isEqualTo("Book updated successfully");
    }
	
}
