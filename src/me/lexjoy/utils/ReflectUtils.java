package me.lexjoy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

  public static Class<?> fetchClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Method fetchMethodByArgs(Class<?> clazz, String methodName, Object...args) {
    if (clazz == null || methodName == null) {
      return null;
    }
    Method[] methods = clazz.getMethods();

    if (methods == null) {
      return null;
    }
    Class<?>[] argTypes = getArgumentTypes(args);

    for (Method m : methods) {
      if (m == null || !methodName.equals(m.getName())) {
        continue;
      }
      if (isValidArgTypes(m.getParameterTypes(), argTypes)) {
        return m;
      }
    }
    return null;
  }

  public static Method fetchMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
    if (clazz == null) {
      return null;
    }
    try {
      return clazz.getMethod(methodName, paramTypes);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Object newInstance(String className) {
    return newInstance(fetchClass(className));
  }

  public static<T> T newInstance(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }
    try {
      return clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Object execMethod(Object receiver, String methodName, Object...args) {
    if (receiver == null) {
      return null;
    }
    return execMethod(receiver, fetchMethodByArgs(receiver.getClass(), methodName, args), args);
  }

  public static Object execMethod(Object receiver, Method method, Object...args) {
    if (method == null) {
      return null;
    }
    try {
      return method.invoke(receiver, args);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Object getFieldValue(Object receiver, String fieldName) {
    return getFieldValue(receiver, getField(receiver, fieldName));
  }

  public static void setFieldValue(Object receiver, String fieldName, Object value) {
    Field field = getField(receiver, fieldName);

    if (field == null) {
      return;
    }
    try {
      field.set(receiver, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  public static Object getPrivateFieldValue(Object receiver, String fieldName) {
    Field field = getField(receiver, fieldName);

    if (field == null) {
      return null;
    }
    field.setAccessible(true);

    Object v = getFieldValue(receiver, field);

    field.setAccessible(false);
    return v;
  }

  public static Object getStaticFieldValue(String className, String fieldName) {
    return getStaticFieldValue(fetchClass(className), fieldName);
  }

  public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
    return getFieldValue(clazz, getFieldByClass(clazz, fieldName));
  }

  private static boolean isValidArgTypes(Class<?>[] paramTypes, Class<?>[] argTypes) {
    if (paramTypes == null) {
      return argTypes == null;
    }
    final int TYPE_SIZE = paramTypes.length;

    if (argTypes.length != TYPE_SIZE) {
      return false;
    }
    Class<?> paramType;
    Class<?> argType;

    for (int i = 0; i < TYPE_SIZE; ++i) {
      paramType = paramTypes[i];
      argType = argTypes[i];

      if (paramType == null || argType == null || !paramType.isAssignableFrom(argType)) {
        return false;
      }
    }
    return true;
  }

  private static Class<?>[] getArgumentTypes(Object...args) {
    if (args == null || args.length == 0) {
      return null;
    }
    final int ARG_SIZE = args.length;
    Class<?>[] argTypes = new Class<?>[ARG_SIZE];
    Object arg;

    for (int i = 0; i < ARG_SIZE; ++i) {
      if ((arg = args[i]) == null) {
        continue;
      }
      argTypes[i] = arg.getClass();
    }
    return argTypes;
  }

  private static Field getField(Object receiver, String fieldName) {
    if (receiver == null) {
      return null;
    }
    return getFieldByClass(receiver.getClass(), fieldName);
  }

  private static Field getFieldByClass(Class<?> clazz, String fieldName) {
    if (clazz == null || fieldName == null) {
      return null;
    }
    try {
      return clazz.getField(fieldName);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Object getFieldValue(Object receiver, Field field) {
    if (receiver == null || field == null) {
      return null;
    }
    try {
      return field.get(receiver);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return null;
  }

  private ReflectUtils() {}

}
