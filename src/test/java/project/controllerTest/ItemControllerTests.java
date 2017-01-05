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
import project.controller.ItemController;
import project.entity.Item;
import project.service.ItemService;

public class ItemControllerTests {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private MockMvc mvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void testGetItems() throws Exception {
	List<Item> list = new ArrayList<>();
	list.add(new Item());
	list.add(new Item());

	Mockito.when(itemService.getAll(1l)).thenReturn(list);

	mvc.perform(get(PathVar.ITEM_FRAGMENT).sessionAttr("curUserId", 1L)).andExpect(status().isOk())
		.andExpect(model().attribute("items", hasSize(2))).andReturn().equals("item");
    }

    @Test
    public void testCreate() throws Exception {
	mvc.perform(post(PathVar.ITEM_CREATE).sessionAttr("curUserId", 1L)
		.param("name", "test")
		.param("category", "test")
		.param("price", "100.99")
		.param("brand", "test")).andExpect(status().isOk()).andReturn().equals("index");
    }

    @Test
    public void testEdit() throws Exception {
	mvc.perform(post(PathVar.ITEM_EDIT).sessionAttr("curUserId", 1L)
		.param("id", "1")
		.param("name", "test")
		.param("category", "test")
		.param("price", "100.99")
		.param("brand", "test")).andExpect(status().isOk()).andReturn().equals("index");
    }

    @Test
    public void testRemove() throws Exception {
	mvc.perform(post(PathVar.ITEM_REMOVE).param("itemArray", "1,2,3")).andExpect(status().isOk())
	.andReturn().equals("index");
    }

}
