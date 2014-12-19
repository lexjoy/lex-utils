package me.lexjoy.utils.log;

public interface LogHandler {
  void v(String tag, String message);
  void i(String tag, String message);
  void w(String tag, String message);
  void d(String tag, String message);
  void e(String tag, String message);
}
