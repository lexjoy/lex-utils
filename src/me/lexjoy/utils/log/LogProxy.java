package me.lexjoy.utils.log;

public abstract class LogProxy implements LogHandler {

  public static enum LogLevel {
    VERBOSE, INFO, WARNING, DEBUG, ERROR
  }

  @Override
  public void v(String tag, String message) {
    this.logMessage(LogLevel.ERROR, tag, message);
  }

  @Override
  public void i(String tag, String message) {
    this.logMessage(LogLevel.ERROR, tag, message);
  }

  @Override
  public void w(String tag, String message) {
    this.logMessage(LogLevel.ERROR, tag, message);
  }

  @Override
  public void d(String tag, String message) {
    this.logMessage(LogLevel.ERROR, tag, message);
  }

  @Override
  public void e(String tag, String message) {
    this.logMessage(LogLevel.ERROR, tag, message);
  }

  protected abstract void logMessage(LogLevel level, String tag, String message);

}
