package com.mikesoft.shoot.operations.saveservers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.exceptions.BusinesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.mikesoft.shoot.exceptions.BuisinesErrorCode.E001;

@Component
@RequiredArgsConstructor
public class ConvertServerSettingVersion {

  public static final TypeReference<List<Map<String, String>>> VALUE_TYPE_REF = new TypeReference<>() {};
  public static final String UNKNOWN_VERSION = "UnknownVersion";

  private final ObjectMapper objectMapper;

  public List<Map<String, String>> prevVersion(File file, String version) throws IOException {
    if (UNKNOWN_VERSION.equals(version)) {
      return nullVersionTo001(file);
    } else {
      throw new BusinesException(E001, version);
    }
  }

  private List<Map<String, String>> nullVersionTo001(File file) throws IOException {
    return objectMapper.readValue(file, VALUE_TYPE_REF);
  }
}
