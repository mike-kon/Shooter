package com.mikesoft.shoot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class SandboxController {

  @PostMapping("sandbox")
  public String getSandbox(Model model) {
    model.addAttribute("username", "koma");
    model.addAttribute("name", "mike");
    List<String> family = List.of("Mike", "Liza", "Lizik", "Anna", "Mary");
    model.addAttribute("family", family);
    return "sandbox";
  }
}
