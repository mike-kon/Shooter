package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.enums.ServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommonController {

  @PostMapping("readTypeServers")
  public Map<ServerType,String> readTypeServers() {
    return ServerType.toMap();
  }

  @PostMapping("ping")
  public String ping() {
    return "passed";
  }

}
