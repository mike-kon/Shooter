package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.operations.saveservers.ServerListOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Controller
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SetupServerRestController {

  private final ServerListOperation serverListOperation;

  @PostMapping("deleteServer")
  public String deleteServer(@RequestParam UUID serverId) {
    if (serverId != null) {
      serverListOperation.deleteServer(serverId);
    }
    // todo возвращать объект, позволяющий обработать ошибку
    return "ok";
  }
}
