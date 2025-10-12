package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.dto.servers.TestServerDto;
import com.mikesoft.shoot.operations.ServerListOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SetupServerController {

  private final ServerListOperation serverListOperation;

  @PostMapping("newServer")
  public String getEmptyController(@RequestParam ServerType serverType, Model model) {
    model.addAttribute("serverType", serverType);
    model.addAttribute("serverName", "?? Новый сервер ??");
    switch (serverType) {
      case TEST:
        return "testSetup";
      case KAFKA:
        return "kafkaSetup";
      case PSQL:
        return "underconstruction";
    }
    return "underconstruction";
  }

  @PostMapping("getSetupServer")
  public String getServerControlController(@RequestParam UUID serverId, Model model) {
    ServerSettingsDto server = serverListOperation.getSavedServer(serverId);
    model.addAttribute("serverType", server.getId());
    model.addAttribute("serverName", server.getServerName());
    if (server instanceof TestServerDto testServerDto) {
      model.addAttribute("value1", testServerDto.getValue1());
      model.addAttribute("value2", testServerDto.getValue2());
      return "testSetup";
    }
    return "underconstruction";
  }

  @PostMapping("createServer")
  public String createServer(@RequestParam Map<String, String> params, Model model) {
    String serverName = params.get("name");
    ServerType serverType = ServerType.valueOf(params.get("serverType"));
    params.remove("name");
    params.remove("serverType");
    serverListOperation.addSavedServer(serverType, serverName, params);
    //todo Надо редиректить на страницу настройки серверов
    return "redirect:/";
  }
}
