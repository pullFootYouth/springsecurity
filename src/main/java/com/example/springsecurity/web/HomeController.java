package com.example.springsecurity.web;

import com.example.springsecurity.util.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class HomeController {


    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/home")
    @ResponseBody
    public Map home(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails subject = UserDetails.class.cast(principal);
            String token = JWTUtil.encode(subject.getUsername());
            return Collections.singletonMap("token", token);
        }

        return Collections.singletonMap("token", "create error");
    }

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("param", "val");
        return "test";
    }

    @RequestMapping("/errorAccess")
    @ResponseBody
    public Map errorAccess() {
        return Collections.singletonMap("error", "access deny");
    }


    @RequestMapping("/notAuthenticate")
    @ResponseBody
    public Map notAuthenticate() {
        return Collections.singletonMap("notAuthenticate", "not authenticate");
    }

}
