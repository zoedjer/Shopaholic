package project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import project.controller.OrderController;
import project.entity.Customer;
import project.entity.Item;
import project.entity.PostAgent;
import project.service.CustomerService;
import project.service.ItemService;
import project.service.OrderBoxService;
import project.service.PostAgentService;

public class OrderControllerTests {
    
    @InjectMocks
    private OrderController orderController;
    
    @Mock
    private PostAgentService postAgentService;
    
    @Mock
    private CustomerService customerService;
    
    @Mock
    private ItemService itemService;
    
    @Mock
    private OrderBoxService orderBoxService;

    private MockMvc mvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    
    @Test
    public void testGetFullOrderInfo() throws Exception {
	List<Customer> cuslist = new ArrayList<>();
	cuslist.add(new Customer(1L));
	cuslist.add(new Customer(2L));
	List<PostAgent> postlist = new ArrayList<>();
	postlist.add(new PostAgent());
	postlist.add(new PostAgent());
	List<Item> itemlist = new ArrayList<>();
	itemlist.add(new Item());
	itemlist.add(new Item());
	Set<String> postNames = new HashSet<>();
	postNames.add("test1");
	postNames.add("test2");
	
	Mockito.when(customerService.getAll(1L, -1)).thenReturn(cuslist);
	Mockito.when(postAgentService.getAll()).thenReturn(postlist);
	Mockito.when(itemService.getAll(1L)).thenReturn(itemlist);
	Mockito.when(postAgentService.getAllPostNames()).thenReturn(postNames);
	
	mvc.perform(get(PathVar.ORDER_FRAGMENT).sessionAttr("curUserId", 1L))
	.andExpect(status().isOk()).andExpect(model().attribute("customers", hasSize(2)))
	.andExpect(model().attribute("postagents", hasSize(2)))
	.andExpect(model().attribute("items", hasSize(2)))
	.andExpect(model().attribute("postnames", hasSize(2)))
	.andReturn().equals("order");
    }
    
    @Test
    public void testOrderDetail() throws Exception {
	List<Map<String, String>> orderlist = new ArrayList<>();
	Map<String, String> map;
	for (int i = 0; i < 2; i++) {
	    map = new HashMap<String, String>();
	    map.put("test"+i, "test"+i);
	    orderlist.add(map);
	}
	
	Mockito.when(orderBoxService.getOrderDetail(1L)).thenReturn(orderlist);
	
	mvc.perform(get(PathVar.ORDER_DETAIL).sessionAttr("curUserId", 1L))
	.andExpect(model().attribute("orders", hasSize(2))).andExpect(status().isOk())
	.andReturn().equals("orderDetail");
    }
    
    @Test
    public void testRemove() throws Exception {
	mvc.perform(post(PathVar.ORDER_REMOVE).param("orderArray", "1,2,3"))
	.andExpect(status().isOk()).andReturn().equals("index");
    }
	
}
