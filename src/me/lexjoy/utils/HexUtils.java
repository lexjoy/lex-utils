package me.lexjoy.utils;

public class HexUtils {

  public static String parseHexText(byte[] data, boolean isUpperCase) {
    if (data == null || data.length == 0) {
      return null;
    }
    StringBuilder sb = new StringBuilder();

    for (byte b : data) {
      sb
        .append(parseHexCharacter(b >> 4, isUpperCase))
        .append(parseHexCharacter(b >> 0, isUpperCase))
        .append(' ');
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  public static char parseHexCharacter(int hexBit, boolean isUpperCase) {
    hexBit &= 0x0F;

    if (hexBit < 0x0A) {
      return shiftCharacter('0', hexBit);
    }
    hexBit -= 0x0A;
    return shiftCharacter(isUpperCase ? 'A' : 'a', hexBit);
  }

  public static char shiftCharacter(char c, int offset) {
    return (char) (c + offset);
  }

  private HexUtils() {}

}
