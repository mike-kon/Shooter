package com.mikesoft.shoot.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.dto.ServerSettingsDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServerListOperation {

  private static final String SAVE_SERVERS_FILE_NAME = "saved_service.json";

  private final ServersFactory serversFactory;
  private final ObjectMapper objectMapper;

  @Value("${application.shooters.save_dir}")
  private String saveDir;

  private final List<ServerSettingsDto> servers = new ArrayList<>();

  @PostConstruct
  public void loadServers() {
    String filePath = saveDir + File.separator + SAVE_SERVERS_FILE_NAME;
    File savedFile = new File(filePath);
    if (savedFile.exists()) {
      try {
        List<Map<String, String>> rawList = objectMapper.readValue(savedFile, new TypeReference<>() {
        });
        List<ServerSettingsDto> loadedServers = rawList.stream()
            .map(serversFactory::createServer)
            .toList();
        servers.clear();
        servers.addAll(loadedServers);
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    } else {
      log.warn("Could not find saved_service.json");
    }
  }

  public List<ServerSettingsDto> getSavedServers() {
    return servers;
  }

  public void savedServer(Map<String, String> params) throws JsonMappingException {
    ServerSettingsDto server;
    if (params.containsKey("id")) {
      // update
      UUID id = UUID.fromString(params.get("id"));
      updateServer(id, params);
    } else {
      // create new
      server = serversFactory.createServer(params);
      servers.add(server);
    }
    saveAllServers();
  }

  private void updateServer(UUID id, Map<String, String> params) throws JsonMappingException {
    ServerSettingsDto server = servers.stream()
        .filter(x -> x.getId().equals(id))
        .findFirst()
        .orElseThrow();
    objectMapper.updateValue(server, params);
  }

  private void saveAllServers() {
    String filePath = saveDir + File.separator + SAVE_SERVERS_FILE_NAME;
    File savedFile = new File(filePath);
    try {
      objectMapper.writeValue(savedFile, servers);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public ServerSettingsDto getSavedServer(UUID id) {
    return servers.stream().filter(server -> server.getId().equals(id)).findFirst().orElse(null);
  }
}
