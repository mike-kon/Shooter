package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.enums.ServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SetupServerController {

  @PostMapping("newServer")
  public String getEmptyController(@RequestParam ServerType serverType) {
    return "underconstruction";
  }
}
