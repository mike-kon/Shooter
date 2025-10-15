package com.mikesoft.shoot.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.enums.ServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.mikesoft.shoot.dto.ServerSettingsDto.SERVER_NAME;
import static com.mikesoft.shoot.dto.ServerSettingsDto.SERVER_TYPE;

@Component
@RequiredArgsConstructor
public class ServersFactory {

  private final ObjectMapper objectMapper;

  public ServerSettingsDto createServer(Map<String, String> params) {
    ServerType serverType = ServerType.valueOf(params.get(SERVER_TYPE));
    params.remove(SERVER_TYPE);
    return create(serverType, params);
  }

  public ServerSettingsDto createServer(ServerType serverType, String serverName) {
    return create(serverType, Map.of(SERVER_NAME, serverName));
  }

  private ServerSettingsDto create(ServerType serverType, Map<String, String> params){
    ServerSettingsDto serverSettingsDto = objectMapper.convertValue(params, serverType.getClassType());
    serverSettingsDto.setServerType(serverType);
    return serverSettingsDto;
  }
}
