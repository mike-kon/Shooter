package com.mikesoft.shoot.exceptions;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.IllegalFormatException;

public abstract class KnownException extends RuntimeException {

  private static final String FULL_MESSAGE_TEMPLATE = "[%c%03d] %s";
  private static final String WRONG_PARAMETERS =
      "!!!Внимание. При вызове класса %s не согласованы параметры. " +
          "Текст шаблона: {%s}, количество аргументов: %d, текст ошибки: %s";

  private final char prefix;
  @Getter
  private final int code;
  private final String templateMessage;
  @Getter
  private final Object[] args;

  protected KnownException(char prefix, int code, String templateMessage, Object... args) {
    this.prefix = prefix;
    this.code = code;
    this.templateMessage = templateMessage;
    this.args = args;
  }

  public String getOnlyMessage() {
    try {
      return String.format(templateMessage, args);
    } catch (IllegalFormatException e) {
      return WRONG_PARAMETERS.formatted(
          this.getClass().getSimpleName(),
          templateMessage,
          Array.getLength(args),
          e.getMessage());
    }
  }

  @Override
  public String getMessage() {
    return String.format(FULL_MESSAGE_TEMPLATE, prefix, code, getOnlyMessage());
  }
}
