package project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.controller.ShopaController;
import project.service.UserService;

public class ShopaControllerTests {

    @InjectMocks
    private ShopaController shopaController;
    
    @Mock
    private UserService userService;
    
    private MockMvc mvc;
    
    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(shopaController).build();
    }
    
    @Test
    public void testLoginPage() throws Exception {
	mvc.perform(get("/login")).andExpect(status().isOk()).andReturn().equals("loginPage");
    }
    @Test
    public void testRegisterPage() throws Exception {
	mvc.perform(get("/register")).andExpect(status().isOk()).andReturn().equals("registerPage");
    }
    @Test
    public void testRegistration() throws Exception {
	mvc.perform(post("/registration").param("email", "a@a.com").param("pswd", "password"))
		.andExpect(status().is3xxRedirection()).andReturn().equals("login");
    }
    @Test
    public void testLogoutPage() throws Exception {
	mvc.perform(get("/logoutPage")).andExpect(status().isOk()).andReturn().equals("logoutPage");
    }
    @Test
    public void testLogout() throws Exception {
	mvc.perform(get("/logout")).andExpect(status().isOk()).andReturn().equals("login");
    }
    @Test
    public void testIndex() throws Exception {
	mvc.perform(get("/dashboard")).andExpect(status().isOk()).andReturn().equals("index");
    }
}
