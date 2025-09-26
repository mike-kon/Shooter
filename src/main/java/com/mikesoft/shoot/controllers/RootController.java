package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.shooters.Shoot;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

  private final Worker worker;

  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping("shooter")
  public String shooter(Model model) {
    Map<String, String> typeList = worker.getAllShooters().stream()
        .collect(Collectors.toMap(Shoot::getName, Shoot::getInfo));
    model.addAttribute("typeList", typeList);
    return "shooter";
  }

  @GetMapping("setupservers")
  public String setupServers(Model model) {
    return "setupservers";
  }

}
