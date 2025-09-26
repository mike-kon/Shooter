package com.mikesoft.shoot.dto;

import com.mikesoft.shoot.dto.enums.ServerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerSettingsDto {
  private ServerType serverType;
}
