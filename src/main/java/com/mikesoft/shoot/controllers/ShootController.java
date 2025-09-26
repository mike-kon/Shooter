package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShootController {
  private final Worker worker;

  @PostMapping(value = "/shoot")
  public String shoot(@RequestParam("typeShoot") String typeShoot, @RequestParam Map<String, String> params) {
    try {
      params.remove("typeShoot");
      ShootResult res = worker.runShooter(typeShoot,params);
      return retResult(res);
    } catch (RuntimeException ex) {
      return ex.getMessage();
    }
  }

  @PostMapping(value = "/save")
  public String save(@RequestParam Map<String, String> params) {
    try {
      ShootResult res = worker.save(params);
      return retResult(res);
    } catch (RuntimeException ex) {
      return ex.getMessage();
    }
  }


  private String retResult(ShootResult res) {
    return switch (res.getResult()) {
      case OK -> res.getInfo();
      case ERROR -> "Error: " + res.getInfo();
    };
  }

}
