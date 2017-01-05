package project.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import project.beanconfig.PathVar;
import project.entity.Customer;
import project.entity.Item;
import project.entity.OrderedItem;
import project.entity.PostAgent;
import project.myUtil.MyJsonParser;
import project.service.CustomerService;
import project.service.ItemService;
import project.service.OrderBoxService;
import project.service.PostAgentService;

@Controller
public class OrderController {

    @Autowired
    private PostAgentService postAgentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderBoxService orderBoxService;

    @RequestMapping(path = PathVar.ORDER_REMOVE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String removeOrder(@RequestParam(value = "orderArray") long[] ids) {
	orderBoxService.removeOrder(ids);
	return "index";
    }

    @RequestMapping(path =PathVar.ORDER_DETAIL, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getOrderDetail(HttpServletRequest req, Model model) {
	Long userId = (Long) req.getSession().getAttribute("curUserId");
	List<Map<String, String>> orders = orderBoxService.getOrderDetail(userId);
	model.addAttribute("orders", orders);
	return "orderDetail";
    }

    @RequestMapping(path = PathVar.ORDER_CREATE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public void newOrder(HttpServletRequest req, @RequestBody String jsonStr) {
	System.out.println(jsonStr);

	Long userId = (Long) req.getSession().getAttribute("curUserId");

	System.out.println(userId);
	
	Map<String, Object> map = MyJsonParser.parseOrderStr(jsonStr);

	Long customerId = (Long) map.get("customerId");
	Long postAgentId = (Long) map.get("postAgentId");
	String trackNum = (String) map.get("trackNum");
	String shipping_fee = (String) map.get("shipping_fee");

	@SuppressWarnings("unchecked")
	List<OrderedItem> itemList = (List<OrderedItem>) map.get("itemList");
	
	orderBoxService.createOrder(userId, customerId, postAgentId, trackNum, shipping_fee, itemList);

    }

    @RequestMapping(path = PathVar.ORDER_FRAGMENT, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getFullOrderInfo(HttpServletRequest req, Model model) {

	Long userId = (Long) req.getSession().getAttribute("curUserId");

	List<Customer> cuslist = customerService.getAll(userId, -1);
	List<PostAgent> postlist = postAgentService.getAll();
	List<Item> itemlist = itemService.getAll(userId);
	Set<String> postNames = postAgentService.getAllPostNames();

	model.addAttribute("customers", cuslist);
	model.addAttribute("postagents", postlist);
	model.addAttribute("items", itemlist);
	model.addAttribute("postnames", postNames);
	return "order";
    }

}
