package me.lexjoy.utils.view;

import me.lexjoy.utils.MatchUtils;
import me.lexjoy.utils.NumericUtils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {

  /**
   * <pre>
   * Usage sample:
   * 
   *   ViewUtils.setTextDrawables(
   *     textView,
   *     Position.LEFT,
   *     resources.getDrawable(drawableId));
   * 
   *   ViewUtils.setTextDrawables(
   *     textView,
   *     Position.BOTTOM | Position.TOP,
   *     resources.getDrawable(   topDrawableId),
   *     resources.getDrawable(bottomDrawableId));
   * 
   * // drawable sequence: left, top, right, bottom(if exists)
   * </pre>
   * 
   * @see Position
   * @see Position#LEFT
   * @see Position#TOP
   * @see Position#RIGHT
   * @see Position#BOTTOM
   */
  public static void setTextDrawables(TextView text, int positionSet, Drawable... drawables) {
    if (MatchUtils.checkEmpty(text, drawables)) {
      return;
    }
    SparseArray<Drawable> drawableMap = new SparseArray<Drawable>();
    int drawableIndex = 0;
    int drawableSize = drawables.length;
    Drawable drawable;

    for (int position : POSITION_SET) {
      if (!NumericUtils.hasFlag(positionSet, position)) {
        continue;
      }
      drawable = drawables[drawableIndex++];

      if (drawable != null) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawableMap.put(position, drawable);
      }

      if (drawableIndex >= drawableSize) {
        break;
      }
    }
    text.setCompoundDrawables(
       drawableMap.get(Position.LEFT  ),
       drawableMap.get(Position.TOP   ),
       drawableMap.get(Position.RIGHT ),
       drawableMap.get(Position.BOTTOM));
  }

  public static void setBackground(View view, Drawable drawable) {
    if (view == null) {
      return;
    }
    _sdkHackImpl.setBackground(view, drawable);
  }

  private ViewUtils() {}

  private static final SDKHack _sdkHackImpl;

  private static final int[] POSITION_SET = {
    Position.TOP, Position.LEFT, Position.RIGHT, Position.BOTTOM};

  private static interface SDKHack {
    void setBackground(View view, Drawable drawable);
  }

  private static class SDKHack_v16_Impl implements SDKHack {
    @Override
    public void setBackground(View view, Drawable drawable) {
      view.setBackground(drawable);
    }
  }

  private static class SDKHack_v10_Impl implements SDKHack {
    @SuppressWarnings("deprecation")
    @Override
    public void setBackground(View view, Drawable drawable) {
      view.setBackgroundDrawable(drawable);
    }
  }

  static {
    if (Build.VERSION.SDK_INT >= 16) {
      _sdkHackImpl = new SDKHack_v16_Impl();
    } else {
      _sdkHackImpl = new SDKHack_v10_Impl();
    }
  }

}
