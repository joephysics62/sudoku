package com.fenton.suguru;

public class SuguruExamples {

  private static final int a = 1;
  private static final int b = 2;
  private static final int c = 3;
  private static final int d = 4;
  private static final int e = 5;
  private static final int f = 6;
  private static final int g = 7;
  private static final int h = 8;
  private static final int i = 9;

  private static final int x = 0;

  public static final Suguru EXAMPLE_ONE
    = new Suguru(
        new int[][] {
          {5, 4, x, x, 5, x},
          {x, x, x, x, x, x},
          {4, x, x, x, x, x},
          {x, x, x, x, 2, x},
          {x, 3, 5, x, x, 5},
          {x, x, x, x, x, x}
        },
        new int[][] {
          {a, a, a, a, b, b},
          {a, c, d, d, d, b},
          {c, c, e, d, d, b},
          {c, e, e, e, f, b},
          {c, g, e, f, f, f},
          {g, g, g, g, f, h}
        });

  public static final Suguru EXAMPLE_TWO
  = new Suguru(
      new int[][] {
        {x, x, x, x, 5, x},
        {x, x, x, x, x, 1},
        {x, x, x, x, 4, x},
        {x, x, x, x, x, x},
        {x, x, x, x, 3, x},
        {x, x, x, 1, x, x}
      },
      new int[][] {
        {a, a, a, b, c, c},
        {d, d, b, b, b, c},
        {e, d, d, b, f, c},
        {e, g, d, f, f, c},
        {g, g, h, h, f, f},
        {g, g, h, h, i, i}
      });

  public static final Suguru EXAMPLE_THREE
  = new Suguru(
      new int[][] {
        {x, 3, x, x, x, x},
        {x, x, x, x, x, x},
        {x, x, 3, x, 2, x},
        {x, x, x, x, x, x},
        {x, x, x, x, x, x},
        {x, x, x, x, x, x}
      },
      new int[][] {
        {a, b, b, c, c, d},
        {b, b, c, c, e, d},
        {b, f, c, e, e, e},
        {f, f, f, g, e, h},
        {i, f, g, g, g, h},
        {i, i, i, g, h, h}
      });

}
