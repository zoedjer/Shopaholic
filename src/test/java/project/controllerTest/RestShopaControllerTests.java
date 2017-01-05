package project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.beanconfig.PathVar;
import project.controller.RestShopaController;
import project.service.OrderBoxService;
import project.service.UserService;

public class RestShopaControllerTests {

    @InjectMocks
    private RestShopaController restShopaController;

    @Mock
    private UserService userService;

    @Mock
    private OrderBoxService orderBoxService;

    private MockMvc mvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(restShopaController).build();
    }

    @Test
    public void testVerifyUserTrue() throws Exception {
	Mockito.when(userService.isExisting("test@test.com")).thenReturn(true);

	mvc.perform(post(PathVar.REST_VERIFY_USER).param("userEmail", "test@test.com")).andExpect(status().isOk())
		.andReturn().equals(true);
    }

    @Test
    public void testVerifyUserFalse() throws Exception {
	Mockito.when(userService.isExisting("test@test.com")).thenReturn(false);

	mvc.perform(post(PathVar.REST_VERIFY_USER).param("userEmail", "test@test.com")).andExpect(status().isOk())
		.andReturn().equals(false);
    }
}
