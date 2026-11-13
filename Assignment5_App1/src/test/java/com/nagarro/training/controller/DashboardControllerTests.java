package com.nagarro.training.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nagarro.training.config.TestConfig;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ServiceUtil;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DashboardControllerTests {
	
	private MockMvc mockMvc;
	
	@Mock
	private ServiceUtil serviceUtil;
	
	@InjectMocks
	private DashboardController dashboardController;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }
	
	@Test
    public void testBackDashboard() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        when(serviceUtil.getAllBooks()).thenReturn(bookList);

        // When & Then
        mockMvc.perform(post("/back"))
            .andExpect(status().isOk())
            .andExpect(view().name("dashboard"))
            .andExpect(model().attribute("bookList", bookList));

        verify(serviceUtil).getAllBooks();
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        String bookId = "1";
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        when(serviceUtil.deleteBook(bookId)).thenReturn(HttpStatus.OK);
        when(serviceUtil.getAllBooks()).thenReturn(bookList);

        // When & Then
        mockMvc.perform(post("/delete")
                .param("id", bookId))
            .andExpect(status().isOk())
            .andExpect(view().name("dashboard"))
            .andExpect(model().attribute("bookList", bookList));

        verify(serviceUtil).deleteBook(bookId);
        verify(serviceUtil).getAllBooks();
    }
    
}
