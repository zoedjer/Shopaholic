package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import project.beanconfig.PathVar;
import project.entity.OrderedItem;
import project.service.OrderBoxService;
import project.service.UserService;

@RestController
public class RestShopaController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderBoxService orderBoxService;

    @Autowired
    private Gson gson;

    @RequestMapping(path = PathVar.REST_VERIFY_USER, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public boolean verifyUserEmail(@RequestParam(value = "userEmail") String email) {

	System.out.println(email);

	return userService.isExisting(email);
    }

    @RequestMapping(path = PathVar.REST_ORDER_ITEMS, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String getOrderItems(@RequestParam(value = "orderId") long orderId) {
	List<OrderedItem> items = orderBoxService.getOrderItems(orderId);
	String item = gson.toJson(items);
	return item;
    }

}
