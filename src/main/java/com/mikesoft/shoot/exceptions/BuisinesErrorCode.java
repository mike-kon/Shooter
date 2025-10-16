package com.mikesoft.shoot.exceptions;

public class BuisinesErrorCode {
  private BuisinesErrorCode() { }

  public static final ErrorCode E001 = new ErrorCode(1,
      "Версия файла сохранения серверов %s не поддерживается и не может быть сконвертирована в текущую");

}
