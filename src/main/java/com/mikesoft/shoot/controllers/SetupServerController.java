package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.operations.ServerListOperation;
import com.mikesoft.shoot.operations.ServersFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SetupServerController {

  private final ServerListOperation serverListOperation;
  private final ServersFactory serversFactory;

  @PostMapping("newServer")
  public String getEmptyController(@RequestParam ServerType serverType, Model model) {
    ServerSettingsDto server = serversFactory.createServer(serverType, "?? Новый сервер ??");
    model.addAttribute("server", server);
    return serverType.getMvcScript();
  }

  @PostMapping("getSetupServer")
  public String getServerControlController(@RequestParam UUID serverId, Model model) {
    ServerSettingsDto server = serverListOperation.getSavedServer(serverId);
    model.addAttribute("server", server);
    return server.getServerType().getMvcScript();
  }

  @PostMapping("saveServer")
  public String saveServer(@RequestParam Map<String, String> params) {
    try {
      serverListOperation.savedServer(params);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    //todo Надо редиректить на страницу настройки серверов
    return "redirect:/";

  }
}
