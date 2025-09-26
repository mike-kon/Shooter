package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommonController {

  private final Worker worker;

  @PostMapping("readTypeServers")
  public Map<ServerType,String> readTypeServers() {
    return ServerType.toMap();
  }

  @PostMapping("ping")
  public String ping() {
    return "passed";
  }

  @PostMapping("readTemplate")
  public List<String> readTemplates() {
    return worker.readSaveds();
  }

}
