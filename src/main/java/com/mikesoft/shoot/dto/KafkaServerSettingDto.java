package com.mikesoft.shoot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaServerSettingDto extends ServerSettingsDto {
  private String url;
  private Integer port;
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
