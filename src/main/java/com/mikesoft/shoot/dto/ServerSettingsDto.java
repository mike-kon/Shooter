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
  private UUID id;
  private ServerType serverType;
  private String serverName;

  protected ServerSettingsDto(ServerType serverType, String serverName) {
    this.id = UUID.randomUUID();
    this.serverType = serverType;
    this.serverName = serverName;
  }
}
