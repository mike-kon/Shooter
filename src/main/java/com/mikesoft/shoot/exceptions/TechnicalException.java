package com.mikesoft.shoot.exceptions;

public class TechnicalException extends KnownException{

  public TechnicalException(ErrorCode code, Object... args) {
    super('S', code.getCode(), code.getMessageTemplate(), args);
  }
}
