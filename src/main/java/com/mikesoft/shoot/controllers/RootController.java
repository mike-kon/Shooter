package com.mikesoft.shoot.controllers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.operations.ServerListOperation;
import com.mikesoft.shoot.shooters.Shoot;
import com.mikesoft.shoot.work.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class RootController {

  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }


  @PostMapping("error")
  public String errorHandler(@RequestBody String params) {
    return "underconstruction";
  }

}
