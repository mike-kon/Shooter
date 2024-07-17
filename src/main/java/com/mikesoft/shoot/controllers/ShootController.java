package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.work.Worker;
import jakarta.servlet.jsp.PageContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShootController {
  private final Worker worker;

  @RequestMapping(value = "/shoot", method = RequestMethod.POST)
  public String shoot(@RequestParam("typeShoot") String typeShoot, @RequestParam Map<String, String> params) {
    try {
      params.remove("typeShoot");
      ShootResult res = worker.runShooter(typeShoot,params);
      return switch (res.getResult()) {
        case OK -> res.getInfo();
        case ERROR -> "Error: " + res.getInfo();
      };
    } catch (RuntimeException ex) {
      return ex.getMessage();
    }
  }
}
