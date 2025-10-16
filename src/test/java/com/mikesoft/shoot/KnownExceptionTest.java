package com.mikesoft.shoot;

import com.mikesoft.shoot.exceptions.KnownException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KnownExceptionTest {

  @Test
  void goodTest() {
    RuntimeException ex = Assertions.assertThrows(KnownException.class, () -> {
      throw new KnownExceptionExample(10, "value1", 2, "value2");
    });
    String actual = ex.getMessage();
    String expected = "[T010] Error: value1, 2, value2";
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void wrongTest1() {
    RuntimeException ex = Assertions.assertThrows(KnownExceptionExample.class, () -> {
      throw new KnownExceptionExample(10, "value1", 2);
    });
    String actual = ex.getMessage();
    String expected = "[T010] !!!Внимание. При вызове класса KnownExceptionExample не согласованы параметры. " +
        "Текст шаблона: {Error: %s, %d, %s}, количество аргументов: 2, текст ошибки: Format specifier '%s'";
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void wrongTest2() {
    RuntimeException ex = Assertions.assertThrows(KnownException.class, () -> {
      throw new KnownExceptionExample(10, "value1", "value2", "value3");
    });
    String actual = ex.getMessage();
    String expected = "[T010] !!!Внимание. При вызове класса KnownExceptionExample не согласованы параметры. " +
        "Текст шаблона: {Error: %s, %d, %s}, количество аргументов: 3, текст ошибки: d != java.lang.String" ;
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void wrongTest3() {
    RuntimeException ex = Assertions.assertThrows(KnownException.class, () -> {
      throw new KnownExceptionWrongExample(10, "value1", "value2", "value3");
    });
    String actual = ex.getMessage();
    String expected = "[T010] !!!Внимание. При вызове класса KnownExceptionWrongExample не согласованы параметры. " +
        "Текст шаблона: {Error: %s, %, %s}, количество аргументов: 3, текст ошибки: Flags = ' ,'";
    Assertions.assertEquals(expected, actual);
  }
}

class KnownExceptionExample extends KnownException {

  private static final String TEMPLATE = "Error: %s, %d, %s";

  public KnownExceptionExample(int code, Object... agrs) {
    super('T', code, TEMPLATE, agrs);
  }
}

class KnownExceptionWrongExample extends KnownException {

  private static final String TEMPLATE = "Error: %s, %, %s";

  public KnownExceptionWrongExample(int code, Object... agrs) {
    super('T', code, TEMPLATE, agrs);
  }
}