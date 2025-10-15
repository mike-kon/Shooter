package com.mikesoft.shoot.dto.enums;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.servers.KafkaServerSettingDto;
import com.mikesoft.shoot.dto.servers.PostgresServerSettingDto;
import com.mikesoft.shoot.dto.servers.TestServerDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum ServerType {
  TEST ("test", "testSetup", TestServerDto.class),
  KAFKA ("kafka", "underconstruction", KafkaServerSettingDto.class),
  PSQL ("postgresql", "underconstruction", PostgresServerSettingDto.class);

  private final String info;
  private final String mvcScript;
  private final Class<? extends ServerSettingsDto> classType;

  public static Map<ServerType,String> toMap() {
    return Arrays.stream(ServerType.values())
        .collect(Collectors.toMap(x -> x, x -> x.info));
  }

}
