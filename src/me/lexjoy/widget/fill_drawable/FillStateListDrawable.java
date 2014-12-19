package me.lexjoy.widget.fill_drawable;

import me.lexjoy.utils.ColorUtils;
import me.lexjoy.utils._assert.AssertUtils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;

public class FillStateListDrawable extends StateListDrawable {

  public FillStateListDrawable(Resources resources) {
    AssertUtils.checkNotEmpty(resources, "invalid resources");
    this.resources = resources;
  }

  public FillStateListDrawable(Resources resources, int colorListId, int drawableId, boolean fitDark) {
    this(resources);
    FillDrawableFactory drawableHelper = new FillDrawableFactory(resources, drawableId);

    ColorStateList colorStateList = resources.getColorStateList(colorListId);
    AssertUtils.checkNotEmpty(colorStateList, "invalid color state list, id: " + colorListId);

    int stateColor;

    for (int[] state : StateList.COMMON_STATES) {
      stateColor = colorStateList.getColorForState(state, UNSET_COLOR);

      if (stateColor == UNSET_COLOR) {
        continue;
      }
      this.addState(state, drawableHelper.getDrawable(stateColor, fitDark));
    }
  }

  public FillStateListDrawable addState(int[] stateSet, int drawableId, int colorId) {
    return this.addState(stateSet, drawableId, colorId, true);
  }

  public FillStateListDrawable addState(int[] stateSet, int drawableId, int colorId, boolean fitDark) {
    this.addState(stateSet, ColorUtils.fillByResources(this.resources, drawableId, colorId, fitDark));
    return this;
  }

  private Resources resources;

  private static final int UNSET_COLOR = 0x00000001;//#00 000001

}
