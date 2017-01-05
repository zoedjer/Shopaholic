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
import project.entity.Item;
import project.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(path = PathVar.ITEM_FRAGMENT, method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public String getItems(HttpServletRequest req, Model model) {

		Long curUserId = (Long) req.getSession().getAttribute("curUserId");
		List<Item> items = itemService.getAll(curUserId);
		model.addAttribute("items", items);
		return "item";
	}

	@RequestMapping(path = PathVar.ITEM_CREATE, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public String createItem(HttpServletRequest req, @RequestParam(value = "name") String name,
			@RequestParam(value = "category") String category, @RequestParam(value = "price") String price,
			@RequestParam(value = "brand") String brand) {
		Long curUserId = (Long) req.getSession().getAttribute("curUserId");
		itemService.createItem(curUserId, name, category, price, brand);

		return "index";
	}

	@RequestMapping(path = PathVar.ITEM_EDIT, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public String editItem(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
			@RequestParam(value = "category") String category, @RequestParam(value = "price") String price,
			@RequestParam(value = "brand") String brand) {

		itemService.editItem(id, name, category, price, brand);

		return "index";
	}

	@RequestMapping(path = PathVar.ITEM_REMOVE, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public String removeItem(@RequestParam(value = "itemArray") long[] ids) {

		itemService.removeItem(ids);
		return "index";
	}
}
