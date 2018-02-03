package com.vanhack.forum;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SecurityTests {
	
	@Autowired
    private WebApplicationContext context;
	
    private MockMvc mockMvc;
  
    @Before
    public void setUp() {
    	mockMvc = MockMvcBuilders
                	.webAppContextSetup(context)
                	.apply(springSecurity())
                	.build();
    }
    
    @Test
    public void testAnonymous() throws Exception {
        mockMvc.perform(get("/api/user"))
        	.andExpect(status().is3xxRedirection());
    }

    @Test
    public void testUserAccessForAccount() throws Exception{
    	mockMvc.perform(get("/api/user")
    		.with(httpBasic("pipecm@gmail.com","vanhack")))
    		.andExpect(authenticated());
    }
    
    @Test
    public void testNotExistingUser() throws Exception{
    	mockMvc.perform(get("/api/user")
    		.with(httpBasic("vanhack@vanhack.com","vanhack00")))
    		.andExpect(unauthenticated());
    }
    
    @Test
    public void testLogin() throws Exception {
    	mockMvc.perform(formLogin("/login")
    			.user("pipecm@gmail.com")
    			.password("vanhack"))
    			.andExpect(authenticated());
    }
    
}
