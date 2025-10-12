package com.mikesoft.shoot.utils.jsonchildern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonChildren {

  private static final String DEF_FIELD_TYPE = "type";

  private final ObjectMapper objectMapper;

  public <T, E extends TypeExtendsClass<T>> List<T> readChildrenFromJson(File file, Class<E> typeClass) throws IOException {
    return readChildrenFromJson(file, typeClass, DEF_FIELD_TYPE);
  }


  public <T, E extends TypeExtendsClass<T>> List<T> readChildrenFromJson(File file, Class<E> typeClass, String typeName)
      throws IOException {
    List<Map<String, Object>> rawList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
    return getList(typeClass, typeName, rawList);
  }

  public <T, E extends TypeExtendsClass<T>> List<T> readChildrenFromJson(String json, Class<E> typeClass)
      throws JsonProcessingException {
    return readChildrenFromJson(json, typeClass, DEF_FIELD_TYPE);
  }

  public <T, E extends TypeExtendsClass<T>> List<T> readChildrenFromJson(String json, Class<E> typeClass,
                                                                         String typeName) throws JsonProcessingException {
    List<Map<String, Object>> rawList = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
    return getList(typeClass, typeName, rawList);
  }

  private <T, E extends TypeExtendsClass<T>> List<T> getList(Class<E> typeClass, String typeName, List<Map<String, Object>> rawList) {
    return rawList.stream()
        .map(raw -> create(raw, typeClass, typeName))
        .toList();
  }

  private <T, E extends TypeExtendsClass<T>> T create(Map<String, Object> rawFields,
                                                      Class<E> typeClass, String typeName) {
    try {
      String tp = rawFields.get(typeName).toString();
      Method meth = typeClass.getMethod("valueOf", String.class);
      E tpEnum = (E) meth.invoke(null, tp);
      Class<? extends T> clazz = tpEnum.getCurrentType();
      return objectMapper.convertValue(rawFields, clazz);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

}
