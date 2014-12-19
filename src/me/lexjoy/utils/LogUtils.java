package me.lexjoy.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.lexjoy.utils.log.LogHandler;

import android.util.Log;

public class LogUtils {

  public static final LogHandler DEFAULT_LOG_HANDLER;

  public static void addLogHandler(LogHandler handler) {
    if (handler == null) {
      return;
    }
    sHandlerList.add(handler);
  }

  public static void removeLogHandler(LogHandler handler) {
    if (handler == null) {
      return;
    }
    sHandlerList.remove(handler);
  }

  public static void v(String tag, String message) {
    logMessage("v", tag, message);
  }

  public static void i(String tag, String message) {
    logMessage("i", tag, message);
  }

  public static void w(String tag, String message) {
    logMessage("w", tag, message);
  }

  public static void d(String tag, String message) {
    logMessage("d", tag, message);
  }

  public static void e(String tag, String message) {
    logMessage("e", tag, message);
  }

  private static void logMessage(String method, String tag, String message) {
    for (LogHandler handler : sHandlerList) {
      ReflectUtils.execMethod(
          handler, findLogMethod(handler, method), tag, message);
    }
  }

  private static Method findLogMethod(LogHandler handler, String name) {
    // assert handler != null;
    return ReflectUtils.fetchMethod(handler.getClass(), name, LOG_METHOD_PARAMTYPES);
  }

  private LogUtils() {}

  private static final List<LogHandler> sHandlerList;

  private static final Class<?>[] LOG_METHOD_PARAMTYPES = {String.class, String.class};

  static {
    sHandlerList = new ArrayList<LogHandler>();
    DEFAULT_LOG_HANDLER = new LogcatLogHandler();
    sHandlerList.add(DEFAULT_LOG_HANDLER);
  }

  private static class LogcatLogHandler implements LogHandler {
    @Override
    public void v(String tag, String message) {
      Log.v(tag, message);
    }

    @Override
    public void i(String tag, String message) {
      Log.i(tag, message);
    }

    @Override
    public void w(String tag, String message) {
      Log.w(tag, message);
    }

    @Override
    public void d(String tag, String message) {
      Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
      Log.e(tag, message);
    }
  }

}
