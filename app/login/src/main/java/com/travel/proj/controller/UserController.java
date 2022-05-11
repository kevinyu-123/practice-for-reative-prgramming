package com.travel.proj.controller;

import com.travel.proj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/register")
    public String register(){
        return "user/registerForm";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/main")
    public String index(){
        return "index";
    }

    @GetMapping("/email-auth")
    public String emailConfirm(@RequestParam String authValue, HttpSession session){
       int result = service.checkAuthCode(authValue,session);
        if(result == 1){
            return "success";
        }else {
            return "error";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session,@RequestParam String email){
        service.logout(session,email);
        return "redirect:/main";
    }
}
