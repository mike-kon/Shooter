package com.mikesoft.shoot.exceptions;

public class BusinesException extends KnownException{

  public BusinesException(ErrorCode code, Object... args) {
    super('E', code.getCode(), code.getMessageTemplate(), args);
  }
}
