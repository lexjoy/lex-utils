package me.lexjoy.view;

import me.lexjoy.utils.DefaultUtils;

import android.view.View;

public abstract class ViewFinder {

  public abstract View findView(int id);

  public<T extends View> T findView(int id, Class<T> viewType) {
    return DefaultUtils.getInstance(this.findView(id), viewType);
  }

}
