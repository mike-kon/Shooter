package com.mikesoft.shoot.shooters;

import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.dto.enums.ShootResultEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShootKafkaLocal implements Shoot {
  private final KafkaTemplate<UUID, String> kafkaTemplate;

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
    String message = params.get("messages");
    UUID key = UUID.randomUUID();
    String topic = params.get("topic").equals("") ? defTopic : params.get("topic");
    Map<String, String> addHeaders =
            Arrays.stream(params.get("headers").split("\n")).map(x -> x.split("="))
                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    List<Header> headers = new ArrayList<>();
    headers.add(new RecordHeader("Result", "Success".getBytes(StandardCharsets.UTF_8)));
    addHeaders.entrySet().forEach(x ->
            headers.add(new RecordHeader(x.getKey(), x.getValue().getBytes(StandardCharsets.UTF_8))));
    log.debug("Producer send message. Topic: {}, Key: {}, Headers: [{}]",
            topic, key,
            String.join(",", headers.stream()
                    .map(x -> String.format("%s->%s", x.key(), new String(x.value(), StandardCharsets.UTF_8))).toList())
    );
    try {
      ProducerRecord<UUID, String> record = new ProducerRecord<>(topic, 0, key, message, headers);
      kafkaTemplate.send(record);
      return new ShootResult(ShootResultEnum.OK, "Выстрел произведен", null);
    } catch (Throwable ex) {
      log.error("{} - {} - Ошибка отправке ответного сообщения: {}", topic, key, ex.getMessage());
      return new ShootResult(ShootResultEnum.ERROR, ex.getMessage(), null);
    }
  }

}
