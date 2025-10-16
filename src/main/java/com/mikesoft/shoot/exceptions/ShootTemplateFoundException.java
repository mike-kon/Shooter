package com.mikesoft.shoot.exceptions;

@Deprecated
public class ShootTemplateFoundException extends RuntimeException{
  private final String shootName;

  public ShootTemplateFoundException(String shootName) {
    this.shootName = shootName;
  }

  @Override
  public String getMessage() {
    return "Template name %s not found ".formatted(shootName);
  }
}
