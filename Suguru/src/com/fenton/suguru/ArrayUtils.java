package com.fenton.suguru;

import java.util.Arrays;

public class ArrayUtils {

  public static int[][] clone(final int[][] arr) {
    final int width = arr[0].length;
    final int[][] cloned = new int[arr.length][width];
    for (int r = 0; r < arr.length; r++) {
      cloned[r] = Arrays.copyOf(arr[r], width);
    }
    return cloned;
  }

}
