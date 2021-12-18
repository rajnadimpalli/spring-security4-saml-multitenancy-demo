package com.multitenant.demo.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {
   @GetMapping("/")
   @PostMapping("/")
   public String index() {
      return "index";
   }

   @GetMapping("/user")
   @PostMapping("/user")
   public String user(Principal principal) {
      // Get authenticated user name from Principal
      System.out.println(principal.getName());
      return "user";
   }
}
