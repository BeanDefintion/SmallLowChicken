package com.xpj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
public class IndexController {

    @RequestMapping(value = "")
    @ResponseBody
    public String helloWorld() {
        return "Hello World!";
    }
}
