package com.mikesoft.shoot.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.utils.jsonchildern.JsonChildren;
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

  private final SaveServersFactory saveServersFactory;
  private final ObjectMapper objectMapper;
  private final JsonChildren jsonChildren;

  @Value("${application.shooters.save_dir}")
  private String saveDir;

  private final List<ServerSettingsDto> servers = new ArrayList<>();

  @PostConstruct
  public void loadServers() {
    String filePath = saveDir + File.separator + SAVE_SERVERS_FILE_NAME;
    File savedFile = new File(filePath);
    if (savedFile.exists()) {
      try {
        List<ServerSettingsDto> loadedServers = jsonChildren.readChildrenFromJson(savedFile, ServerType.class, "serverType");
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

  public void addSavedServer(ServerType serverType, String serverName, Map<String, String> params) {
    ServerSettingsDto newServer = saveServersFactory.createServer(serverType, serverName, params);
    servers.add(newServer);
    saveServer();
  }

  private void saveServer() {
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
