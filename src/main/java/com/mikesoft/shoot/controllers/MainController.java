package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.operations.ServerListOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/mvc/v1")
@RequiredArgsConstructor
public class MainController {

  private final ServerListOperation serverListOperation;

  @PostMapping("setupservers")
  public String setupServers(Model model) {
    List<ServerSettingsDto> savedServerList = serverListOperation.getSavedServers();
    model.addAttribute("savedServerList", savedServerList);
    return "setupservers";
  }

}
