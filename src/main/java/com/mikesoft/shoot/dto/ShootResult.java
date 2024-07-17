package com.mikesoft.shoot.dto;

import com.mikesoft.shoot.dto.enums.ShootResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShootResult {
  private ShootResultEnum result;
  private String info;
  private List<Object> resultParams;
}
