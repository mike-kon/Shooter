package com.mikesoft.shoot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.exceptions.ShootDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

  private static final char JSON_OPEN_CHAR = '{';
  private static final char TAB_SEPARATOR_CHAR = '\t';
  private static final char TAB_EQUAL_CHAR = '=';
  private static final String NEW_LINE_STRING_LINUX = "\n";
  private static final String NEW_LINE_STRING_WINDOWS = "\r\n";

  private final ObjectMapper mapper;
  
  public  Map<String, String> createHeaders(String message) {
    return makeMapFromString(message);
  }

  public void sendMessage(String bootstrapServer, String topic, Map<String, String> headers, String message) throws ExecutionException, InterruptedException {
    KafkaTemplate<UUID, String> kafkaTemplate = ctreateKafkaClient(bootstrapServer);
    List<Header> kafkaHeaders = new ArrayList<>();
    kafkaHeaders.add(new RecordHeader("Result", "Success".getBytes(StandardCharsets.UTF_8)));
    headers.forEach((key, value) ->
        kafkaHeaders.add(new RecordHeader(key, value.getBytes(StandardCharsets.UTF_8))));
    UUID key = UUID.randomUUID();
    ProducerRecord<UUID, String> rec = new ProducerRecord<>(topic, 0, key, message, kafkaHeaders);
    CompletableFuture<SendResult<UUID, String>> res = kafkaTemplate.send(rec);
    SendResult<UUID, String> res1 = res.get();
    log.info("Sending record to topic {} with headers {}", topic, res1.toString());
  }

  private KafkaTemplate<UUID, String> ctreateKafkaClient(String bootstrapServer) {
    Map<String, Object> config = new HashMap<>();
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    config.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
    config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, AdminClientConfig.DEFAULT_SECURITY_PROTOCOL);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    ProducerFactory<UUID, String> producer = new DefaultKafkaProducerFactory<>(config);
    return new KafkaTemplate<>(producer);
  }

  private Map<String, String> makeMapFromString(String messageHeader) {
    if (messageHeader == null || messageHeader.isEmpty()) {
      return Map.of();
    }
    if (messageHeader.trim().charAt(0) == JSON_OPEN_CHAR) {
      return makeMapFromJson(messageHeader);
    }
    if (messageHeader.chars().anyMatch(ch -> ch == TAB_SEPARATOR_CHAR)) {
      return makeMapFromDelim(messageHeader, TAB_SEPARATOR_CHAR);
    }
    if (messageHeader.chars().anyMatch(ch -> ch == TAB_EQUAL_CHAR)) {
      return makeMapFromDelim(messageHeader, TAB_EQUAL_CHAR);
    }
    throw new ShootDataException("Непонятно, как преобразовать текст к списку заголовков");
  }

  private Map<String, String> makeMapFromDelim(String messageHeader, char delim) {
    return messageHeader.contains(NEW_LINE_STRING_WINDOWS)
        ? makeMapFromDelimList(messageHeader, delim, NEW_LINE_STRING_WINDOWS)
        : makeMapFromDelimList(messageHeader, delim, NEW_LINE_STRING_LINUX);
  }

  private Map<String, String> makeMapFromDelimList(String messageHeader, char delim, String newLine) {
    return Arrays.stream(messageHeader.split(newLine))
        .map(x -> x.split(String.valueOf(delim)))
        .collect(Collectors.toMap(k -> k[0],v -> v[1]));
  }

  private Map<String, String> makeMapFromJson(String messageHeader) {
    try {
      return mapper.readValue(messageHeader, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new ShootDataException("ошибка преобразования в JSON");
    }
  }

}
