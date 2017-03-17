package com.fenton.cellblocks;

import java.util.LinkedHashMap;
import java.util.Map;

public class Codewords {
  private static final int x = 0;

  public static int[][] GRID_ONE = {
      {x, 12,  x,  4,  x,  5,  x,  6,  x, 23,  x,  3,  x},
      {5, 23, 12, 22,  1,  1, 15,  3,  x, 15,  1, 22, 12},
      {x, 17,  x,  5,  x, 18,  x, 22,  x,  1,  x,  5,  x},
     {13, 23, 12, 16,  x, 25, 18,  1, 12, 25, 18, 18,  5},
      {x,  x,  x,  7,  x, 10,  x,  x,  x, 16,  x,  x,  x},
      {2,  1, 15, 16, 10,  x, 13, 22,  2,  2, 23, 13, 17},
      {x, 18,  x, 15,  x, 17,  x,  1,  x,  x,  x, 20,  x},
     {17, 25, 23,  3, 21, 23,  x,  2, 15, 13, 12, 15, 10},
      {x, 15,  x,  x,  x, 13,  x, 23,  x, 23,  x,  8,  x},
     {15, 10, 19, 16, 23, 17, 24,  x, 26, 15, 21, 23,  5},
     { x,  x,  x, 17,  x,  x,  x, 15,  x, 14,  x,  x,  x},
     {11, 16, 19, 15, 17, 16, 18, 10,  x, 22, 26, 18, 10},
     { x,  6,  x,  3,  x, 10,  x, 10,  x, 15,  x, 24,  x},
     {12, 16, 10, 16,  x, 17,  1, 22,  7,  8,  3, 23, 13},
     { x, 13,  x,  7,  x, 18,  x,  3,  x, 23,  x,  9,  x}
  };

  public static Map<Integer, Character> GRID_ONE_CLUES = new LinkedHashMap<>();
  {
    GRID_ONE_CLUES.put(6,  'B');
    GRID_ONE_CLUES.put(25, 'W');
  }
}
