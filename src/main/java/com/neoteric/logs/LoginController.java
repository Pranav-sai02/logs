package com.neoteric.logs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage() {
        logger.info(" Displaying login page");
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpServletRequest request) {
        logger.info(" Login attempt for username: {}", username);

        if (loginService.authenticate(username, password)) {
            logger.info(" Login successful for user: {}", username);
            request.getSession().setAttribute("user", username);
            return "redirect:/home";
        } else {
            logger.warn(" Login failed for user: {}", username);
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "login";
        }
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        String user = (String) request.getSession().getAttribute("user");
        if (user == null) {
            logger.warn(" Unauthorized access to /home - redirecting to login");
            return "redirect:/login";
        }
        logger.info(" User '{}' accessed the home page", user);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        String user = (String) request.getSession().getAttribute("user");
        if (user != null) {
            logger.info(" User '{}' logged out", user);
        }
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/logs")
    public String viewLogs(HttpServletRequest request, Model model) {
        String user = (String) request.getSession().getAttribute("user");

        if (user == null) {
            System.out.println("User not logged in. Redirecting to login.");
            return "redirect:/login";
        }

        System.out.println("User " + user + " is accessing logs.");
        model.addAttribute("logList", loginService.getAllLogs());
        return "logs";
    }



}
