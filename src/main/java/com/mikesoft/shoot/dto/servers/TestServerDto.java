package com.mikesoft.shoot.dto.servers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestServerDto extends ServerSettingsDto {

  private String value1;
  private String value2;

}
