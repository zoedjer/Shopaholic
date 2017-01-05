package project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.beanconfig.PathVar;
import project.controller.PostAgentController;
import project.entity.PostAgent;
import project.service.PostAgentService;

public class PostAgentControllerTests {

    @InjectMocks
    private PostAgentController postAgentController;

    @Mock
    private PostAgentService postAgentService;

    private MockMvc mvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(postAgentController).build();
    }

    @Test
    public void testGetPostAgent() throws Exception {
	List<PostAgent> list = new ArrayList<>();
	list.add(new PostAgent());
	list.add(new PostAgent());

	Mockito.when(postAgentService.getAll()).thenReturn(list);

	mvc.perform(get(PathVar.POST_AGENT_FRAGMENT)).andExpect(status().isOk())
		.andExpect(model().attribute("postagents", hasSize(2))).andReturn().equals("postagent");
    }

    @Test
    public void testCreate() throws Exception {
	mvc.perform(post(PathVar.POST_AGENT_CREATE).param("postName", "test").param("postWebsite", "test"))
		.andExpect(status().isOk()).andReturn().equals("index");
    }

    @Test
    public void testEdit() throws Exception {
	mvc.perform(post(PathVar.POST_AGENT_EDIT).param("postId", "1").param("postName", "test").param("postWebsite",
		"test")).andExpect(status().isOk()).andReturn().equals("index");
    }

    @Test
    public void testRemove() throws Exception {
	mvc.perform(post(PathVar.POST_AGENT_REMOVE).param("postArray", "1,2,3")).andExpect(status().isOk()).andReturn()
		.equals("index");
    }
}
