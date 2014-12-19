package me.lexjoy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public class BroadcastUtils {

  public static void register(Context context, BroadcastReceiver receiver, String... actions) {
    if (MatchUtils.checkEmpty(context, actions)) {
      return;
    }
    for (String action : actions) {
      if (MatchUtils.isEmpty(action)) {
        continue;
      }
      context.registerReceiver(receiver, new IntentFilter(action));
    }
  }

  private BroadcastUtils() {}

}
