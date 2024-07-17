package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.shooters.Shoot;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

  private final Worker worker;

  @GetMapping("/")
  public String index(Model model) {
    Map<String, String> typeList = worker.getAllShooters().stream()
            .collect(Collectors.toMap(Shoot::getName, Shoot::getInfo));
    model.addAttribute("typeList", typeList);
    return "index";
  }

  @PostMapping("/api/v1/loadClass")
  public String loadClass(@RequestParam("jspName") String name) {
    return worker.getAllShooters().stream().filter(x -> x.getName().equals(name)).findFirst().get().getJspFile();
  }
}
