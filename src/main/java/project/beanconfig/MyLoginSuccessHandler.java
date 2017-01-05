package project.beanconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import project.service.UserService;

public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    
    /**
     *  successfully login action
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	    Authentication authentication) throws ServletException, IOException {
	HttpSession session = request.getSession();

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	System.out.println(auth.getName());

	Long userId = userService.getUserId(auth.getName());

	if (session != null) {
	    System.out.println(session.getId());
	    session.setAttribute("curUserId", userId);
	    System.out.println(session.getAttribute("curUserId"));
	} else {
	    System.out.println("session null");
	}

	getRedirectStrategy().sendRedirect(request, response, "/dashboard");
    }

}