package org.company.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller  //-> to render the html page while @RestController = @Controller + @ResponseBody which is used to render a JSON body.
public class LoginController {
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }
}
