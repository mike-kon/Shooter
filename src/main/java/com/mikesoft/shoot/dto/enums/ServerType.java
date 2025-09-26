package com.mikesoft.shoot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum ServerType {
  KAFKA ("kafka"),
  PSQL ("postgresql");

  private final String info;

  public static Map<ServerType,String> toMap() {
    return Arrays.stream(ServerType.values())
        .collect(Collectors.toMap(x -> x, x -> x.info));
  }
}
