package com.mikesoft.shoot.shooters;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.dto.enums.ShootResultEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmptyTest implements Shoot {
  @Override
  public String getName() {
    return "empty";
  }

  @Override
  public String getInfo() {
    return "Пустой";
  }

  @Override
  public String getJspFile() {
    return "empty";
  }

  @Override
  public ShootResult shoot(Map<String,String> params) {
    return new ShootResult(ShootResultEnum.OK,
            String.join(";", params.entrySet().stream().map(x -> x.getKey() + "=>" + x.getValue()).toList()),
            null);
  }
}
