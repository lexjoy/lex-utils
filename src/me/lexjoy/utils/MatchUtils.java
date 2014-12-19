package me.lexjoy.utils;

import java.util.Collection;

public class MatchUtils {

  public static boolean isEmpty(Object o) {
    if (o == null) {
      return true;
    }
    Class<?> oType = o.getClass();

    if (oType.isAssignableFrom(String.class)) {
      return "".equals(o);
    }
    if (oType.isArray()) {
      return isEqual(ReflectUtils.getFieldValue(o, "length"), 0);
    }
    if (oType.isAssignableFrom(Collection.class)) {
      return Collection.class.cast(o).isEmpty();
    }
    return false;
  }

  public static boolean checkEmpty(Object... args) {
    if (args == null || args.length == 0) {
      return true;
    }
    for (Object o : args) {
      if (isEmpty(o)) {
        continue;
      }
      return false;
    }
    return true;
  }

  public static boolean isEqual(Object e1, Object e2) {
    return e1 == null ? e2 == null : e1.equals(e2);
  }

  private MatchUtils() {}

}
