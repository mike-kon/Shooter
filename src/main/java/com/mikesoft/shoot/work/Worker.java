package com.mikesoft.shoot.work;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.exceptions.ShootNotFoundException;
import com.mikesoft.shoot.shooters.Shoot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Getter
public class Worker {
  private final List<Shoot> allShooters;

  public Shoot getShooter(String name){
    Optional<Shoot> shoot = allShooters.stream().filter(x -> name.equals(x.getName())).findFirst();
    if (shoot.isPresent()){
      return shoot.get();
    } else  {
      throw new ShootNotFoundException(name);
    }
  }

  public ShootResult runShooter(String name, Map<String,String> params){
    Shoot shoot = getShooter(name);
    return shoot.shoot(params);
  }
}
