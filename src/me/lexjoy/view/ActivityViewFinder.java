package me.lexjoy.view;

import android.app.Activity;
import android.view.View;

import me.lexjoy.utils._assert.AssertUtils;

public class ActivityViewFinder extends ViewFinder {

  public ActivityViewFinder(Activity activity) {
    AssertUtils.checkNotEmpty(activity, "invalid activity");
    this.activity = activity;
  }

  @Override
  public View findView(int id) {
    return this.activity.findViewById(id);
  }

  private Activity activity;

}
