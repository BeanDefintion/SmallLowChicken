package com.xpj.controller;

import com.xpj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
public class HelloController {

    @Autowired(required = false)
    UserService userService;

    @RequestMapping(value = "/hello")
    public String helloWorld(@RequestParam(name = "name") String name) {
        userService.findBySokeNo();
        return "hello" + name;
    }

}
