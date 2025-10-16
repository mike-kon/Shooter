package com.mikesoft.shoot.exceptions;

@Deprecated
public class ShootNotFoundException extends RuntimeException{
  private final String shootName;

  public ShootNotFoundException(String shootName) {
    this.shootName = shootName;
  }

  @Override
  public String getMessage() {
    return "Shooter with name %s not found ".formatted(shootName);
  }
}
