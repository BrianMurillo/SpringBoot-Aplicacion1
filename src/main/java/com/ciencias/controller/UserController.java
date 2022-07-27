package com.ciencias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/userForm")
    public String getRegistro(){
        return "user-form/user-view";
    }

}
