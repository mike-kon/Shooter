package com.mikesoft.shoot.operations.saveservers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.servers.SaveServerSettingDto;
import com.mikesoft.shoot.exceptions.KnownException;
import com.mikesoft.shoot.exceptions.TechnicalException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.mikesoft.shoot.exceptions.TechnicalErrorCode.T001;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServerListOperation {

  private static final String SAVE_SERVERS_FILE_NAME = "saved_service.json";
  private static final String SAVE_FILE_VERSION = "1.0";

  private final ServersFactory serversFactory;
  private final ObjectMapper objectMapper;
  private final ConvertServerSettingVersion convert;

  @Value("${application.shooters.save_dir}")
  private String saveDir;

  private final List<ServerSettingsDto> servers = new ArrayList<>();

  @PostConstruct
  @SuppressWarnings({"squid:S2209","squid:S1141"})
  public void loadServers() {
    try {
      String filePath = saveDir + File.separator + SAVE_SERVERS_FILE_NAME;
      File savedFile = new File(filePath);
      List<Map<String, String>> rawList;
      if (savedFile.exists()) {
        boolean rewrite = false;
        try {
          SaveServerSettingRawDto saved = objectMapper.readValue(savedFile, SaveServerSettingRawDto.class);
          String loaded = saved.getVersion();
          rawList = SAVE_FILE_VERSION.equals(loaded)
              ? saved.getServersRaw()
              : convert.prevVersion(savedFile, loaded);
        } catch (JsonProcessingException je) {
          rawList = convert.prevVersion(savedFile, ConvertServerSettingVersion.UNKNOWN_VERSION);
          rewrite = true;
        }
        List<ServerSettingsDto> loadedServers = rawList.stream()
            .map(serversFactory::createServer)
            .toList();
        servers.clear();
        servers.addAll(loadedServers);
        if (rewrite) {
          saveAllServers();
        }
      } else {
        throw new TechnicalException(T001, SAVE_SERVERS_FILE_NAME, "file not found");
      }
    } catch (KnownException e) {
      log.error(e.getMessage(), e);
      log.warn("Файл {} при сохранении будет перезаписан", SAVE_SERVERS_FILE_NAME);
    } catch (IOException e) {
      TechnicalException ex = new TechnicalException(T001, SAVE_SERVERS_FILE_NAME, e.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  public List<ServerSettingsDto> getSavedServers() {
    return servers;
  }

  public void savedServer(Map<String, String> params) throws JsonMappingException {
    ServerSettingsDto server;
    if (params.containsKey("id") && Strings.isNotEmpty(params.get("id"))) {
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
      SaveServerSettingDto saveObjects = new SaveServerSettingDto();
      saveObjects.setVersion(SAVE_FILE_VERSION);
      saveObjects.setCreated(Instant.now());
      saveObjects.setServers(servers);
      objectMapper.writeValue(savedFile, saveObjects);
    } catch (IOException e) {
      // todo Сделать нормальное сообщение при ошибке
      log.error(e.getMessage());
    }
  }

  public ServerSettingsDto getSavedServer(UUID id) {
    return servers.stream().filter(server -> server.getId().equals(id)).findFirst().orElse(null);
  }

  public void deleteServer(UUID serverId) {
    servers.removeIf(x -> x.getId().equals(serverId));
    saveAllServers();
  }

  @Getter
  @Setter
  private static class SaveServerSettingRawDto {
    private Instant created;
    private String version;
    @JsonProperty("servers")
    private List<Map<String, String>> serversRaw;
  }
}
