package com.mikesoft.shoot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettingDataDto {
  private List<ServerSettingsDto> servers;
}
