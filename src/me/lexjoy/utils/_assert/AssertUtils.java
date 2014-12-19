package me.lexjoy.utils._assert;

import me.lexjoy.utils.DefaultUtils;
import me.lexjoy.utils.ReflectUtils;

import android.content.Context;

public class AssertUtils {

  public static void checkExpression(boolean expression, String errorInfo) {
    if (_checker.check(expression)) {
      return;
    }
    throw new AssertException(errorInfo);
  }

  public static void checkNotEmpty(Object object, String errorInfo) {
    checkExpression(object != null, errorInfo);
  }

  // framework method
  public static void _init(Context appContext) {
    assert appContext != null: "invalid app context";

    // {:package}.BuildConfig.DEBUG == true
    if (!DefaultUtils.getValue((Boolean) ReflectUtils.getStaticFieldValue(
          appContext.getPackageName() + ".BuildConfig", "DEBUG"), false)) {
      return;
    }
    _checker = new NormalExpressionChecker();
  }

  private AssertUtils() {}

  private static ExpresssionChecker _checker = new BlankExpressionChecker();

  private static interface ExpresssionChecker {
    boolean check(boolean expression);
  }

  private static class BlankExpressionChecker implements ExpresssionChecker {
    @Override
    public boolean check(boolean expression) {
      return true;
    }
  }

  private static class NormalExpressionChecker implements ExpresssionChecker {
    @Override
    public boolean check(boolean expression) {
      return expression;
    }
  }

}
