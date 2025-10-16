package com.mikesoft.shoot.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorCode {

  private final int code;
  private final String messageTemplate;

}
