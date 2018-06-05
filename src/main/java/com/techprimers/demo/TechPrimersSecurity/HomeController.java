package com.techprimers.demo.TechPrimersSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("sessionId")
public class HomeController {

	/*
	 * Spring Security is a framework created by spring uses Authorization (provides
	 * user roles) and Authentication (login based on credentials) mechanism to
	 * control how you access the web pages in your application
	 */
	// no security assigned to this page
	@RequestMapping("/")
	public ModelAndView index() {

		return new ModelAndView("welcome");
	}

	// authorize this method to be called only when the role is assigned as admin
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/secured")
	public ModelAndView hello() {

		return new ModelAndView("result");
	}
	
	// for non-admin users
	@RequestMapping("/alternate")
	public ModelAndView hello2() {

		return new ModelAndView("result");
	}

	/*
	 * Here firstly we identified if user was authenticated before using
	 * SecurityContextHolder.getContext().getAuthentication(). If he/she was, then
	 * we called SecurityContextLogoutHandler().logout(request, response, auth) to
	 * logout user properly.
	 * 
	 * This logout call performs following:
	 * 
	 * Invalidates HTTP Session ,then unbinds any objects bound to it. Removes the
	 * Authentication from the SecurityContext to prevent issues with concurrent
	 * requests. Explicitly clears the context value from the current thread.
	 */

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";// You can redirect wherever you want, but generally it's a good practice to
										// show login screen again.
	}

}
