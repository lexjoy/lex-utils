package me.lexjoy.utils;

public class NumericUtils {

  public static int sumRightShifts(int num, int... shifts) {
    if (shifts == null) {
      return num;
    }
    int sum = 0;

    for (int shift : shifts) {
      sum += num >>> shift;
    }
    return sum;
  }

  public static boolean hasFlag(int flagSet, int flag) {
    return (flagSet & flag) == flag;
  }

  private NumericUtils() {}

}
