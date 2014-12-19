package me.lexjoy.utils;

public class DefaultUtils {

  public static<T> T getValue(T value, T defValue) {
    return value == null ? defValue : value;
  }

  public static<T> T getInstance(Object value, Class<T> valueType) {
    if (valueType == null || !valueType.isInstance(value)) {
      return null;
    }
    return valueType.cast(value);
  }

  private DefaultUtils() {}

}
