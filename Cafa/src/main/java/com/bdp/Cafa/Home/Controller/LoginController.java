package com.bdp.Cafa.Home.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/rules")
    public String hello(Model model) {
        model.addAttribute("activePage", "rules");
        return "rules";
    }

    @GetMapping("/approval")
    public String approval(Model model) {
        model.addAttribute("activePage", "approval");
        return "approval";
    }

    @GetMapping("/log")
    public String log(Model model) {
        model.addAttribute("activePage", "log");
        return "log";
    }

    @GetMapping("/laporan")
    public String laporan(Model model) {
        model.addAttribute("activePage", "laporan");
        return "laporan";
    }
}
