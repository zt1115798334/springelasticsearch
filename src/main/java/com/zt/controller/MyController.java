package com.zt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/myController")
public class MyController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "111";
    }
}
