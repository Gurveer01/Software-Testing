package com.nagarro.training.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.nagarro.training.config.TestConfig;
import com.nagarro.training.dao.LoginDao;
import com.nagarro.training.model.Book;
import com.nagarro.training.util.ServiceUtil;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTests {
	
	private MockMvc mockMvc;
	
	@Mock
    private LoginDao loginDao;

    @Mock
    private ServiceUtil serviceUtil;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }
    
    @Test
    public void testLogin_SuccessfulLogin() throws Exception {
    	
        String username = "testUser";
        String password = "testPass";

        HttpSession session = mock(HttpSession.class);

        when(loginDao.checkLogin(username, password)).thenReturn(true);

        List<Book> mockBookList = new ArrayList<>();
        mockBookList.add(new Book());
        when(serviceUtil.getAllBooks()).thenReturn(mockBookList);

        mockMvc.perform(post("/login")
                .param("username", username)
                .param("password", password)
                .sessionAttr("username", username))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("bookList", mockBookList))
                .andExpect(model().attributeExists("bookList"));

        verify(loginDao).checkLogin(username, password);
        verify(serviceUtil).getAllBooks();
    }

    @Test
    public void testLogin_FailedLogin() throws Exception {
    	
        String username = "testUser";
        String password = "testPass";

        when(loginDao.checkLogin(username, password)).thenReturn(false);

        mockMvc.perform(post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("bookList"));

        verify(loginDao).checkLogin(username, password);
        verify(serviceUtil, never()).getAllBooks();
    }
}
