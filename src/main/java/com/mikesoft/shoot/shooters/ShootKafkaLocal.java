package com.mikesoft.shoot.shooters;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.dto.enums.ShootResultEnum;
import com.mikesoft.shoot.services.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShootKafkaLocal implements Shoot {

  private final KafkaService kafkaService;

  @Value("${application.shooters.kafkalocal.topic}")
  private String defTopic;

  @Override
  public String getName() {
    return "KafkaLocal";
  }

  @Override
  public String getInfo() {
    return "Запрос в локальную кафку";
  }

  @Override
  public String getJspFile() {
    return "kafka";
  }

  @Override
  public ShootResult shoot(Map<String, String> params) {
    UUID key = UUID.randomUUID();
    String topic = params.get("topic").isEmpty() ? defTopic : params.get("topic");

    Map<String, String> headers = kafkaService.createHeaders(params.get("headers"));
    try {
      kafkaService.sendMessage(params.get("bootstrap") , topic, headers,  params.get("messages"));
      return new ShootResult(ShootResultEnum.OK, "Выстрел произведен", null);
    } catch (Exception ex) {
      log.error("{} - {} - Ошибка отправке ответного сообщения: {}", topic, key, ex.getMessage());
      return new ShootResult(ShootResultEnum.ERROR, ex.getMessage(), null);
    }
  }

}
