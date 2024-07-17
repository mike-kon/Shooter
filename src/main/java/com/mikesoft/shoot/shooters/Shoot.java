package com.mikesoft.shoot.shooters;

import com.mikesoft.shoot.dto.ShootResult;

import java.util.Map;

public interface Shoot {
  String getName();
  String getInfo();
  String getJspFile();
  ShootResult shoot(Map<String,String> params);
}
