package me.lexjoy.widget.fill_drawable;

public interface StateList {

  int[] PRESSED_ENABLED = {
    android.R.attr.state_pressed,
    android.R.attr.state_enabled
  };

  int[] CHECKED_ENABLED = {
    android.R.attr.state_checked
  };

  int[] SELECTED_ENABLED = {
    android.R.attr.state_selected,
    android.R.attr.state_enabled
  };

  int[] FOCUSED_ENABLED = {
    android.R.attr.state_focused,
    android.R.attr.state_enabled
  };

  int[] ENABLED = {
    android.R.attr.state_enabled
  };

  int[] CHECKED_DISABLED = {
    android.R.attr.state_checked
  };

  int[] FOCUSED_DISABLED = {
    android.R.attr.state_focused
  };

  int[] DISABLED = {
  };

  int[][] COMMON_STATES = {
    PRESSED_ENABLED ,
    CHECKED_ENABLED ,
    SELECTED_ENABLED,
    FOCUSED_ENABLED ,
    ENABLED         ,
    CHECKED_DISABLED,
    FOCUSED_DISABLED,
    DISABLED
  };

}
