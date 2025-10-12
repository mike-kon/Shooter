package com.mikesoft.shoot.dto.servers;

import com.mikesoft.shoot.dto.ServerSettingsDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaServerSettingDto extends ServerSettingsDto {
  private String url;
  private Boolean usedSsl;
  private SslSetting sslSetting;

  @Getter
  @Setter
  public static class SslSetting {
    private String trustKeyPath;
    private String trustStorePath;
    private String keyStorePath;
  }
}
