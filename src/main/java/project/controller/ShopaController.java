package project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import project.service.FeedbackService;
import project.service.UserService;

@Controller
public class ShopaController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FeedbackService feedbackService;
    
    @RequestMapping(path = "/feedback", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String saveFeedback(HttpServletRequest req,@RequestParam(value="message") String message) {
	Long curUserId = (Long) req.getSession().getAttribute("curUserId");
	System.out.println(message);
	feedbackService.createFeedback(curUserId, message);
	return "index";
    }
    
    @RequestMapping(path = "/feedback", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getFeedback() {
	return "feedbackPage";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String registerUser() {
	return "registerPage";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void registration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	String email = req.getParameter("email");
	String pswd = req.getParameter("pswd");
	
	System.out.println(email + ", " + pswd + ", " + pswd);
	
	userService.createUser(email, email, pswd);
	
	resp.sendRedirect("/login");
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String simple() {

	return "loginPage";
    }
    
    @RequestMapping(path = "/logoutPage", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getLogoutPage() {

	return "logout";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String logout(HttpServletRequest req, HttpServletResponse resp) {

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	if (auth != null) {
	    new SecurityContextLogoutHandler().logout(req, resp, auth);
	}

	return "login";
    }

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String display(Model model) {

	return "index";
    }
}
