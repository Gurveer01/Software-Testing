package com.nagarro.training.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.config.TestConfig;
import com.nagarro.training.model.Author;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ServiceUtil;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EditBookControllerTests {
	
	private MockMvc mockMvc;
	
	@Mock
    private ServiceUtil serviceUtil;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EditBookController editBookController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(editBookController).build();
    }

    @Test
    public void testGetEditView() throws Exception {

        String bookId = "1";
        Book mockBook = new Book();
        mockBook.setId(1);
        mockBook.setName("Sample Book");

        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author(1, "Test Author", null));

        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));


        when(serviceUtil.getBook(bookId)).thenReturn(mockBook);
        when(serviceUtil.getAuthors()).thenReturn(mockAuthors);


        mockMvc.perform(post("/editView").param("id", bookId))
            .andExpect(status().isOk())
            .andExpect(view().name("editpage"))
            .andExpect(model().attribute("authors", mockAuthors))
            .andExpect(model().attribute("date", formattedDate))
            .andExpect(model().attribute("book", mockBook));

        verify(serviceUtil).getBook(bookId);
        verify(serviceUtil).getAuthors();
    }

    @Test
    public void testEditBook() throws Exception {

        Author mockAuthor = new Author(1, "Test Author", null);
        Book mockBook = new Book();
        mockBook.setId(1);
        mockBook.setName("Updated Book");
        mockBook.setAuthor(mockAuthor);
        mockBook.setDate("18 June 2024");

        when(serviceUtil.getAuthor(anyString())).thenReturn(mockAuthor);
        when(objectMapper.writeValueAsString(any(Book.class))).thenReturn("{\"id\":501,\"name\":\"Updated Book\",\"author\":{\"id\":1,\"name\":\"Test Author\"},\"date\":\"18 June 2024\"}");
        when(serviceUtil.updateBook(anyString(), anyString())).thenReturn("Success");


        mockMvc.perform(post("/editBook")
                .param("bookCode", "501")
                .param("bookName", "Updated Book")
                .param("authorId", "1")
                .param("date", "18 June 2024"))
            .andExpect(status().isOk())
            .andExpect(view().name("dashboard"))
            .andExpect(model().attributeExists("bookList"))
            .andExpect(model().attribute("bookList", instanceOf(List.class)));

        verify(serviceUtil).getAuthor(anyString());
        verify(objectMapper).writeValueAsString(any(Book.class));
        verify(serviceUtil).updateBook(anyString(), anyString());
        verify(serviceUtil).getAllBooks();
    }
}
