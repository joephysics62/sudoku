package com.fenton.suguru;

import com.fenton.puzzle.Solution;

public class SuguruMain {

  public static void main(final String[] args) throws Exception {
    final Suguru suguru = SuguruExamples.EXAMPLE_ONE;
    final Solution solution = suguru.solve();
    System.out.println(solution.getType());
    System.out.println(solution.getGrid().get().asPossiblesString());
    System.out.println(solution.getRecurseCount());

  }

}
