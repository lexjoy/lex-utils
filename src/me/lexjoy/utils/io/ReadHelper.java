package me.lexjoy.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadHelper {

  public static final int DEFAULT_BUFFER_SIZE = 1024;

  public static final int DEFAULT_CHECK_MILLISECONDS = 100;

  public ReadHelper() {
    this(DEFAULT_BUFFER_SIZE, DEFAULT_CHECK_MILLISECONDS);
  }

  public ReadHelper(int bufferSize, int checkMillis) {
    this.setBufferSize(bufferSize);
    this.setCheckDuration(checkMillis);
  }

  public ReadHelper setBufferSize(int bufferSize) {
    if (bufferSize <= 0) {
      bufferSize = DEFAULT_BUFFER_SIZE;
    }
    this.bufferSize = bufferSize;
    return this;
  }

  public ReadHelper setCheckDuration(int checkMillis) {
    this.checkMillis = checkMillis;
    return this;
  }

  public byte[] read(InputStream is) throws IOException {
    if (is == null) {
      return null;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final int BUFFER_SIZE = this.bufferSize;
    final int CHECK_DURATION = this.checkMillis;
    final boolean HAS_CHECK = CHECK_DURATION >= 0;
    byte[] buffer = new byte[BUFFER_SIZE];
    int readCount;

    try {
      while (true) {
        readCount = is.read(buffer);
  
        if (readCount > BUFFER_SIZE || readCount < 0) {
          throw new IOException("invalid read size");
        }
        baos.write(buffer, 0, readCount);

        if (HAS_CHECK) {
          try {
            Thread.sleep(CHECK_DURATION);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
  
        if (is.available() <= 0) {
          break;
        }
      }
      return baos.toByteArray();

    } catch (IOException e) {
      throw e;

    } finally {

      try {
        baos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private int bufferSize;
  private int checkMillis;

}
