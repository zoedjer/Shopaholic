package project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import project.beanconfig.PathVar;
import project.entity.Customer;
import project.service.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = PathVar.CUSTOMER_FRAGMENT, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getCustomer(HttpServletRequest req, Model model) {

	Long curUserId = (Long) req.getSession().getAttribute("curUserId");

	// -1 mean all customer at one query, if not -1 means 10 customers from
	// the value
	List<Customer> cuslist = customerService.getAll(curUserId, -1);

	model.addAttribute("customers", cuslist);

	return "customer";
    }

    @RequestMapping(path = PathVar.CUSTOMER_CREATE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String createCustomer(HttpServletRequest req, @RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName, @RequestParam(value = "phone") String phone,
	    @RequestParam(value = "email") String email, @RequestParam(value = "lineOne") String lineOne,
	    @RequestParam(value = "lineTwo") String lineTwo, @RequestParam(value = "city") String city,
	    @RequestParam(value = "county") String county, @RequestParam(value = "country") String country) {

	Long curUserId = (Long) req.getSession().getAttribute("curUserId");
	customerService.createCustomer(curUserId, firstName, lastName, phone, email, lineOne, lineTwo, city, county,
		country);

	return "index";
    }

    @RequestMapping(path = PathVar.CUSTOMER_EDIT, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String editCustomer(@RequestParam(value = "id") Long id, @RequestParam(value = "firstName") String firstName,
	    @RequestParam(value = "lastName") String lastName, @RequestParam(value = "phone") String phone,
	    @RequestParam(value = "email") String email, @RequestParam(value = "lineOne") String lineOne,
	    @RequestParam(value = "lineTwo") String lineTwo, @RequestParam(value = "city") String city,
	    @RequestParam(value = "county") String county, @RequestParam(value = "country") String country) {

	customerService.editCustomer(id, firstName, lastName, phone, email, lineOne, lineTwo, city, county, country);

	return "index";
    }

    @RequestMapping(path = PathVar.CUSTOMER_REMOVE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String removeCustomer(@RequestParam(value = "customerArray") long[] ids) {

	customerService.removeCustomer(ids);

	return "index";
    }

    @RequestMapping(path = PathVar.CUSTOMER_ADD_ADDRESS, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String addAddress(@RequestParam(value = "customerId") Long id,
	    @RequestParam(value = "lineOne") String lineOne, @RequestParam(value = "lineTwo") String lineTwo,
	    @RequestParam(value = "city") String city, @RequestParam(value = "county") String county,
	    @RequestParam(value = "country") String country) {

	customerService.addAddress(id, lineOne, lineTwo, city, county, country);

	return "index";
    }
}
