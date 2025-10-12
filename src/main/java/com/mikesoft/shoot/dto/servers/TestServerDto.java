package com.mikesoft.shoot.dto.servers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.mikesoft.shoot.dto.enums.ServerType.TEST;

@Getter
@Setter
@NoArgsConstructor
public class TestServerDto extends ServerSettingsDto {

  public TestServerDto(String serverName, String value1, String value2) {
    super(TEST, serverName);
    this.value1 = value1;
    this.value2 = value2;
  }

  private String value1;
  private String value2;
}
