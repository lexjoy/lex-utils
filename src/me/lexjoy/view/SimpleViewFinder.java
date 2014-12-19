package me.lexjoy.view;

import me.lexjoy.utils._assert.AssertUtils;

import android.view.View;

public class SimpleViewFinder extends ViewFinder {

  public SimpleViewFinder(View rootView) {
    AssertUtils.checkNotEmpty(rootView, "invalid root view");
    this.rootView = rootView;
  }

  @Override
  public View findView(int id) {
    return this.rootView.findViewById(id);
  }

  private View rootView;

}
