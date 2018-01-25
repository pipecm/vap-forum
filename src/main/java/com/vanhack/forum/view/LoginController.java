package com.vanhack.forum.view;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;

@Controller
public class LoginController {
	
	private static final Logger log = LogManager.getLogger(LoginController.class);

	@Autowired
	private UserController userController;
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");
		return modelAndView;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userController.findByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		} else {
			try {
				userController.addUser(user);
				modelAndView.addObject("successMessage", "User has been registered successfully");
				modelAndView.addObject("user", new User());
				modelAndView.setViewName("signup");
			} catch(UserException e) {
				log.error(e.getMessage(), e);
			}
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userController.findByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getNickname() + " " + " (" + user.getEmail() + ")");
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
