package com.fenton.suguru;

import java.util.ArrayList;
import java.util.List;

public class SuguruMain {

  public static void main(final String[] args) throws Exception {
    final Suguru suguru = SuguruExamples.EXAMPLE_ONE;
    final Solution solution = suguru.solve();
    System.out.println(solution.getType());
    printState(solution.getGrid().get());
  }

  private static void printState(final int[][] currentState) {
    for (final int[] row : currentState) {
      System.out.print("|");
      for (final int bitValue : row) {
        final List<Integer> decomposed = decomposed(bitValue);
        System.out.print(decomposed.size() == 1 ? decomposed.get(0) : "?");
        System.out.print("|");
      }
      System.out.println();
    }
  }

  private static List<Integer> decomposed(final int bitValue) {
    final List<Integer> powers = new ArrayList<>();
    int n = bitValue; // something > 0
    int power = 0;
    while (n != 0) {
        if ((n & 1) != 0) {
            powers.add(power + 1);
        }
        ++power;
        n >>>= 1;
    }
    return powers;
  }
}
