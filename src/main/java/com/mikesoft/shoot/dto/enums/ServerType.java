package com.mikesoft.shoot.dto.enums;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.servers.KafkaServerSettingDto;
import com.mikesoft.shoot.dto.servers.PostgresServerSettingDto;
import com.mikesoft.shoot.dto.servers.TestServerDto;
import com.mikesoft.shoot.utils.jsonchildern.TypeExtendsClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum ServerType implements TypeExtendsClass<ServerSettingsDto> {
  TEST ("test", TestServerDto.class),
  KAFKA ("kafka", KafkaServerSettingDto.class),
  PSQL ("postgresql", PostgresServerSettingDto.class);

  @Getter
  private final String info;

  private final Class<? extends ServerSettingsDto> classType;

  public static Map<ServerType,String> toMap() {
    return Arrays.stream(ServerType.values())
        .collect(Collectors.toMap(x -> x, x -> x.info));
  }

  @Override
  public Class<? extends ServerSettingsDto> getCurrentType() {
    return classType;
  }
}
