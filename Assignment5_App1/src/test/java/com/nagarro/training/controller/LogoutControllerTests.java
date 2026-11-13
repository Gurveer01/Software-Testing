package com.nagarro.training.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nagarro.training.config.TestConfig;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class LogoutControllerTests {
	
	private MockMvc mockMvc;
	
	@InjectMocks
    private LogoutController logoutController;
	
	@Mock
	private MockHttpSession session;
	
	@Mock
	private MockHttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build();
    }
    
    @Test
    public void testLogout() throws Exception {
    	
    	when(request.getSession()).thenReturn(session);
        session.setAttribute("username", "testUser");

        mockMvc.perform(post("/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(session).removeAttribute("username");
        verify(session).invalidate();
    }

}
