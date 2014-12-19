package me.lexjoy.utils;

import me.lexjoy.utils._assert.AssertUtils;

import android.content.Context;

public class LexFramework {

  /**
   * Invoke this method at {:Application}.onCreate() method<br/>
   * 
   *   or use {@linkplain me.lexjoy.app.BaseApplication} as your manifest app name.
   * 
   * @param appContext
   */
  public static void _init(Context appContext) {
    GlobalUtils._init(appContext);
    AssertUtils._init(appContext);
  }

  private LexFramework() {}

}
