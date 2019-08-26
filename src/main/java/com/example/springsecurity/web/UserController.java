package com.example.springsecurity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping("/user")
    public Map retrieveUser() {

        return Collections.singletonMap("username", "default");
    }
}
