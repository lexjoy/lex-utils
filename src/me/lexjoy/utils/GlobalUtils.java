package me.lexjoy.utils;

import android.content.Context;
import android.os.Handler;

public class GlobalUtils {

  public static void postRunner(Runnable runner) {
    if (runner == null) {
      return;
    }
    sHandler.post(runner);
  }

  public static void postRunner(Runnable runner, long delayMillis) {
    if (runner == null) {
      return;
    }
    sHandler.postDelayed(runner, delayMillis);
  }

  public static void clearRunner(Runnable runner) {
    sHandler.removeCallbacks(runner);
  }

  /**
   * Make sure this method was invoked under ui-thread
   * or use {@link me.lexjoy.app.BaseApplication} as application name of your AndroidManifest.xml
   */
  public static void _init(Context appContext) {
  }

  private GlobalUtils() {}

  private static final Handler sHandler = new Handler();

}
