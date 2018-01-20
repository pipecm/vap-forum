package com.vanhack.forum.view;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;

@Controller
public class SignUpController extends WebMvcConfigurerAdapter {
	
	@Autowired
	private UserController userController;
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
    }
	
	@GetMapping("/signup")
    public String showForm(User user) {
		return "signup";
	}
	
	@PostMapping("/signup")
    public String checkUser(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
            return "signup";
        }
		
		try {
			userController.addUser(user);			
		} catch(UserException e) {
			return "signup";
		}
		return "redirect:/home";
	}
}
