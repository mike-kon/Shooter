package com.mikesoft.shoot.dto.servers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class SaveServerSettingDto {
  private Instant created;
  private String version;
  private List<ServerSettingsDto> servers;
}
