package com.principle.checkinproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	@GetMapping("/login")
    public String login() {
        return "login";  // This will return the login.html page
    }

    @GetMapping("/home")
    public String home() {
        return "home";  // You will create a home.html page for the success page
    }

}
