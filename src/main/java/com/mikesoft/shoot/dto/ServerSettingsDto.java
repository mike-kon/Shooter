package com.mikesoft.shoot.dto;

import com.mikesoft.shoot.dto.enums.ServerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class ServerSettingsDto {

  public static final String SERVER_NAME = "serverName";
  public static final String SERVER_TYPE = "serverType";

  private UUID id;
  private ServerType serverType;
  private String serverName;

}
