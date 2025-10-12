package com.mikesoft.shoot.operations;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import com.mikesoft.shoot.dto.enums.ServerType;
import com.mikesoft.shoot.dto.servers.TestServerDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SaveServersFactory {

  public ServerSettingsDto createServer(ServerType serverType, String serverName, Map<String, String> params) {
    return switch (serverType) {
      case TEST -> new TestServerDto(serverName, params.get("value1"), params.get("value2"));
      default -> throw  new IllegalArgumentException("Invalid server type");
    };
  }
}
