package com.mikesoft.shoot.util.jsonchildern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesoft.shoot.utils.jsonchildern.JsonChildren;
import com.mikesoft.shoot.utils.jsonchildern.TypeExtendsClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonChildrenTest {

  static final String JSONTEXT = """
      [
      	{
      		"type": "A",
      		"name": "one",
      		"number": 123,
      		"info": "class A"
      	},
      	{
      		"type": "B",
      		"name": "two",
      		"number": 5242,
      		"data": [
      			"class B",
      			"this is not of B",
      			"ok"
      		]
      	}
      ]
      """;
  @Test
  void readChildrenFromJsonTest() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonChildren jsonChildren = new JsonChildren(objectMapper);

    List<Boo> res =
        jsonChildren.readChildrenFromJson(JSONTEXT, MyType.class);
    assertNotNull(res, "не доделано");
    assertEquals(2, res.size());
    Boo res1 = res.get(0);
    assertEquals(MyType.A, res1.type);
    assertEquals(A.class, res1.getClass());
    assertEquals("one", res1.name);
    assertEquals(123, res1.number);
    A a = (A) res1;
    assertEquals("class A", a.info);

    Boo res2 = res.get(1);
    assertEquals(MyType.B, res2.type);
    assertEquals(B.class, res2.getClass());
    assertEquals("two", res2.name);
    assertEquals(5242, res2.number);
    B b = (B) res2;
    assertEquals(3, b.data.size());
    assertEquals("ok", b.data.get(2));
  }

  @RequiredArgsConstructor
  public enum MyType implements TypeExtendsClass<Boo> {
    A(A.class),
    B(B.class);

    private final Class<? extends Boo> typeCls;
    @Override
    public Class<? extends Boo> getCurrentType() {
      return typeCls;
    }
  }

  @Getter
  @Setter
  public abstract static class Boo {
    private MyType type;
    private String name;
    private Integer number;
  }

  @Getter
  @Setter
  public static class A extends Boo {
    private String info;
  }

  @Getter
  @Setter
  public static class B extends Boo {
    private List<String> data;
  }
}
