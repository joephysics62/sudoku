package com.fenton.cellblocks;

public class KillerSudokus {

  private static final int C = -1;  // means left
  private static final int A = -2;  // means up
  private static final int P = -3;  // means right

  public static final int[][] GRID_OTHER = {
    { 4, 23,  C,  C, 12, 22,  C,  5, 13},
    { A,  A, 23, 16,  A,  C,  A,  A,  A},
    { 9,  P,  A,  A,  C,  C, 11, 11,  C},
    { A, 12,  C, 27, 19,  P,  A, 11, 17},
    {19,  C,  A,  A,  A,  A,  P,  A,  A},
    { A,  A,  P,  A,  A,  C, 22,  9,  C},
    { 9,  C, 20,  C,  C,  C,  A,  C,  3},
    {17,  C, 12,  C, 21, 13,  C,  C,  A},
    {10,  C,  3,  C,  A,  C,  C, 12,  C}

  };

}
