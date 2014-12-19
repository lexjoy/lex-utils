package me.lexjoy.utils;

import java.io.IOException;
import java.io.InputStream;

import me.lexjoy.utils.io.ReadHelper;

public class IOUtils {

  public static byte[] read(InputStream is) throws IOException {
    return new ReadHelper().read(is);
  }

  private IOUtils() {}

}
