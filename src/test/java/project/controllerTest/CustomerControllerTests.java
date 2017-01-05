package project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.beanconfig.PathVar;
import project.controller.CustomerController;
import project.entity.Customer;
import project.service.CustomerService;

public class CustomerControllerTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomer() throws Exception {
	List<Customer> customers = new ArrayList<>();

	for (long i = 0; i < 2; i++) {
	    customers.add(new Customer(i));
	}

	Mockito.when(customerService.getAll(1L, -1)).thenReturn(customers);
	
	mvc.perform(get(PathVar.CUSTOMER_FRAGMENT).sessionAttr("curUserId", 1L)).andExpect(status().isOk())
		.andExpect(model().attribute("customers", hasSize(2))).andReturn().equals("customer");
    }

    @Test
    public void testCreate() throws Exception {
	mvc.perform(post(PathVar.CUSTOMER_CREATE).sessionAttr("curUserId", 1L)
		.param("firstName", "firstName")
		.param("lastName", "lastName")
		.param("phone", "firstName")
		.param("email", "firstName")
		.param("lineOne", "firstName")
		.param("lineTwo", "firstName")
		.param("city", "firstName")
		.param("county", "firstName")
		.param("country", "firstName")
		).andExpect(status().isOk())
	.andReturn().equals("index");
    }

    @Test
    public void testEdit() throws Exception {
	mvc.perform(post(PathVar.CUSTOMER_EDIT)
		.param("id", "1")
		.param("firstName", "firstName")
		.param("lastName", "lastName")
		.param("phone", "firstName")
		.param("email", "firstName")
		.param("lineOne", "firstName")
		.param("lineTwo", "firstName")
		.param("city", "firstName")
		.param("county", "firstName")
		.param("country", "firstName")
		).andExpect(status().isOk())
	.andReturn().equals("index");
    }

    @Test
    public void testRemove() throws Exception {
	mvc.perform(post(PathVar.CUSTOMER_REMOVE)
		.param("customerArray","1,2")
		
		).andExpect(status().isOk())
	.andReturn().equals("index");
    }

    @Test
    public void testAddAddress() throws Exception {

	mvc.perform(post(PathVar.CUSTOMER_ADD_ADDRESS)
		.param("id", "1")
		.param("customerId", "1")
		.param("lineOne", "firstName")
		.param("lineTwo", "firstName")
		.param("city", "firstName")
		.param("county", "firstName")
		.param("country", "firstName")
		).andExpect(status().isOk())
	.andReturn().equals("index");
    }
}
