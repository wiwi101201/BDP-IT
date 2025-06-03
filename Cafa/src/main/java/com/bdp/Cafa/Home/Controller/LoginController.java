package com.bdp.Cafa.Home.Controller.;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // file: src/main/resources/templates/login.html
    }

    @GetMapping("/")
    public String homePage() {
        return "logout"; // file: src/main/resources/templates/logout.html
    }
}
