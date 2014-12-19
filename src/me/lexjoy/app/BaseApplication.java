package me.lexjoy.app;

import me.lexjoy.utils.LexFramework;
import android.app.Application;

public class BaseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    LexFramework._init(this);
  }

}
