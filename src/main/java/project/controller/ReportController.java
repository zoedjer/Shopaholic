package project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;

import project.beanconfig.PathVar;
import project.entity.ProfitNode;
import project.service.OrderBoxService;

@Controller
public class ReportController {

    @Autowired
    private Gson gson;

    @Autowired
    private OrderBoxService orderService;

    private Object dataHolder;

    private boolean isChange = false;

    @RequestMapping(path = PathVar.REPORT_FRAGMENT, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getReport(HttpServletRequest req, Model model) {

	Long curUserId = (Long) req.getSession().getAttribute("curUserId");

	if (!isChange) {

	    String start = req.getParameter("fromDate");
	    String end = req.getParameter("toDate");

	    System.out.println(start + ", " + end);

	    List<ProfitNode> nodes = orderService.getProfitByDate(curUserId, start, end);

	    dataHolder = gson.toJson(nodes);

	    System.out.println(dataHolder);
	}
	model.addAttribute("data", dataHolder);

	return "report";
    }

    @RequestMapping(path = PathVar.REPORT_FRAGMENT, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String getProfit(HttpServletRequest req, Model model) {

	Long curUserId = (Long) req.getSession().getAttribute("curUserId");

	String start = req.getParameter("fromDate");
	String end = req.getParameter("toDate");

	System.out.println(start + ", " + end);

	List<ProfitNode> nodes = orderService.getProfitByDate(curUserId, start, end);

	dataHolder = gson.toJson(nodes);

	System.out.println(dataHolder);

	isChange = true;

	model.addAttribute("data", dataHolder);

	return "index";
    }
    
}
