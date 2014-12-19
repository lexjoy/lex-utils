package me.lexjoy.widget.fill_drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import me.lexjoy.utils.ColorUtils;
import me.lexjoy.utils._assert.AssertUtils;

public class FillDrawableFactory {

  public FillDrawableFactory(Resources resources, int drawableId) {
    AssertUtils.checkNotEmpty(resources, "invalid resources");
    Bitmap src = BitmapFactory.decodeResource(resources, drawableId);
    AssertUtils.checkNotEmpty(src, "bitmap not found");

    int width  = src.getWidth ();
    int height = src.getHeight();
    int alphaMatrix[][] = new int[width][height];
    int grayMatrix [][] = new int[width][height];

    int color;

    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        color = src.getPixel(x, y);
        alphaMatrix[x][y] = Color.alpha(color);
        grayMatrix [x][y] = ColorUtils.measureGray(color);
      }
    }
    this.src = src;
    this.grayMatrix  = grayMatrix;
    this.alphaMatrix = alphaMatrix;
    this.width = width;
    this.height = height;
    this.resources = resources;
  }

  public Drawable getDrawable(int color, boolean fitDark) {
    Bitmap src = this.src;
    int alphaMatrix[][] = this.alphaMatrix;
    int grayMatrix [][] = this.grayMatrix;

    int startX = 0;
    int startY = 0;
    int endX  = this.width;
    int endY  = this.height;

    int fillColor = color;
    int a, r, g, b;
    int gray;
    int offset;
    Bitmap bitmap;
    Drawable drawable;
    bitmap = src.copy(Bitmap.Config.ARGB_8888, true);

    a = Color.alpha(fillColor);
    r = Color.red  (fillColor);
    g = Color.green(fillColor);
    b = Color.blue (fillColor);

    // fitDark:
    //   bit = bit - bit * gray / 0xff
    //
    // otherwise:
    //   bit = bit + gray - bit * gray / 0xff
    for (int x = startX; x < endX; ++x) {
      for (int y = startY; y < endY; ++y) {
        gray   = grayMatrix [x][y];
        offset = fitDark ? 0 : gray;
        bitmap.setPixel(x, y, Color.argb(
          measureColorPercent(a, alphaMatrix[x][y]),
          measureColorBit(r, gray, offset),
          measureColorBit(g, gray, offset),
          measureColorBit(b, gray, offset)));
      }
    }
    byte[] nigePatchChunk = src.getNinePatchChunk();

    if (nigePatchChunk == null) {
      drawable = new BitmapDrawable(resources, bitmap);
    } else {
      drawable = new NinePatchDrawable(resources, new NinePatch(bitmap, nigePatchChunk, null));
    }
    return drawable;
  }

  public Drawable getDrawableByResources(int colorId, boolean fitDark) {
    return this.getDrawable(this.getColor(colorId), fitDark);
  }

  private int getColor(int colorId) {
    return this.resources.getColor(colorId);
  }

  private static int measureColorBit(int colorBit, int gray, int offset) {
    return offset + colorBit - measureColorPercent(colorBit, gray);
  }

  private static int measureColorPercent(int colorBit, int percentBit) {
    return (colorBit * percentBit) >>> 8;
  }

  private Resources resources;
  private Bitmap src;
  private int[][] alphaMatrix;
  private int[][] grayMatrix;
  private int width;
  private int height;

}
