package com.mikesoft.shoot.exceptions;

public class TechnicalErrorCode {
  private TechnicalErrorCode() { }

  public static final ErrorCode T001 = new ErrorCode(1,
  "Файл настройки %s неизвестного формата, битый или отсутствует. Текст ошибки: %s");
}
