package com.mikesoft.shoot.work;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.dto.ShootResult;
import com.mikesoft.shoot.exceptions.ShootNotFoundException;
import com.mikesoft.shoot.shooters.Shoot;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mikesoft.shoot.dto.enums.ShootResultEnum.ERROR;
import static com.mikesoft.shoot.dto.enums.ShootResultEnum.OK;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
@Deprecated
public class Worker {

  private static final String PROJECT_NAME_PARAM = "projectName";
  private static final String FILE_NAME_EXTENSION = ".json";
  private final List<Shoot> allShooters;
  private final ObjectMapper objectMapper;

  @Value("${application.shooters.save_dir}")
  private String saveDir;

  @PostConstruct
  public void createDir() throws FileNotFoundException {
    File dir = new File(saveDir);
    if (dir.exists()) {
      if (dir.isDirectory()) {
        log.info("Directory {} exists.", saveDir);
      } else {
        log.error("File {} exist, but is not a dirrectory.", saveDir);
        throw new FileNotFoundException(saveDir + " is not a dir");
      }
    } else {
      if (!dir.mkdirs()) {
        throw new FileNotFoundException("Cannot create directory " + saveDir);
      }
    }
  }

  public Shoot getShooter(String name){
    Optional<Shoot> shoot = allShooters.stream().filter(x -> name.equals(x.getName())).findFirst();
    if (shoot.isPresent()){
      return shoot.get();
    } else  {
      throw new ShootNotFoundException(name);
    }
  }

  public ShootResult runShooter(String name, Map<String,String> params){
    Shoot shoot = getShooter(name);
    return shoot.shoot(params);
  }

  public ShootResult save(Map<String, String> params) {
    try {
      String json = objectMapper.writeValueAsString(params);
      String fileName = params.get(PROJECT_NAME_PARAM) + FILE_NAME_EXTENSION;
      String filePath = Paths.get(saveDir, fileName).toString();
      Files.write(Paths.get(filePath), json.getBytes(), StandardOpenOption.CREATE_NEW);
      return new ShootResult(OK, "File %s saved".formatted(fileName), null);
    } catch (IOException e) {
      return new ShootResult(ERROR, e.getMessage(), null);
    }
  }

  public List<String> readSaveds() {
    Path path = Paths.get(saveDir);

    try (Stream<Path> files = Files.walk(path)){
          return files.filter(Files::isRegularFile)
          .map(x -> x.toFile().getName())
          .toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Map<String, String> readTemplateFromFile(String filename) {
    if (filename == null) {
      throw new RuntimeException(new NullPointerException("template filename"));
    }
    Path filePath = Paths.get(saveDir, filename);
    if (Files.exists(filePath)) {
      try {
        return objectMapper.readValue(filePath.toFile(), Map.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new RuntimeException(new FileNotFoundException(filename));
    }
  }
}
