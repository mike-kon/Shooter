package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.shooters.Shoot;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

  private final Worker worker;

  @PostMapping("loadClass")
  @Deprecated
  public String loadClass(@RequestParam("jspName") String name) {
    return worker.getAllShooters().stream()
        .filter(x -> x.getName().equals(name))
        .findFirst()
        .orElseThrow()
        .getJspFile();
  }

  @PostMapping("loadFromTemplate")
  @Deprecated
  public String loadFromTemplate(@RequestParam("templateName") String templateName, Model model) {
    Map<String, String> template = worker.readTemplateFromFile(templateName);
    String typeShootName = template.get("typeShoot");
    Shoot shoot = worker.getAllShooters().stream()
        .filter(x -> x.getName().equals(typeShootName))
        .findFirst()
        .orElseThrow();
    return shoot.getJspFile();
  }

}
