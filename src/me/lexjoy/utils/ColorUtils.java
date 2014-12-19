package me.lexjoy.utils;

import me.lexjoy.widget.fill_drawable.FillDrawableFactory;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ColorUtils {

  /**
   * <pre>
   * gray = r * 0.299 + g * 0.587 + b * 0.114
   *      = r >>> 2,5,6,9,13,15,16
   *      + g >>> 1,4,6,7,10,14,16
   *      + b >>> 3,4,5,7,10,12,13,14
   * since:
   *   0xff >>> 8 = 0;
   * 
   * gray = r >>> 2,5,6
   *      + g >>> 1,4,6,7
   *      + b >>> 3,4,5,7
   * </pre>
   * 
   * @param color
   * @return
   */
  public static int measureGray(int color) {
    return NumericUtils.sumRightShifts(Color.red  (color), 2, 5, 6   )
        +  NumericUtils.sumRightShifts(Color.green(color), 1, 4, 6, 7)
        +  NumericUtils.sumRightShifts(Color.blue (color), 3, 4, 5, 7);
  }

  public static Drawable fill(Resources resources, int drawableId, int color, boolean fitDark) {
    return new FillDrawableFactory(resources, drawableId).getDrawable(color, fitDark);
  }

  public static Drawable fillByResources(Resources resources, int drawableId, int colorId, boolean fitDark) {
    return new FillDrawableFactory(resources, drawableId).getDrawableByResources(colorId, fitDark);
  }

  private ColorUtils() {}

}
