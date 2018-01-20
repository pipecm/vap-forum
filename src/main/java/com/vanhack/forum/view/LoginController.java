package com.vanhack.forum.view;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;

public class LoginController extends WebMvcConfigurerAdapter {

	@Autowired
	private UserController userController;
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
    }
	
	@GetMapping("/login")
    public String showForm(User user) {
		return "login";
	}
	
	@PostMapping("/login")
    public String checkUser(User user, BindingResult result) {
		if (result.hasErrors()) {
            return "login";
        }
		
		User loginUser = userController.findByEmail(user.getEmail());
		if(loginUser.getPassword().equals(user.getPassword())) {
			return "redirect:/home";
		}
		return "login";
	}
}
