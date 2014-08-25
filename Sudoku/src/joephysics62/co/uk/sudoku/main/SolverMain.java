package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.standard.StandardParser;
import joephysics62.co.uk.sudoku.standard.StandardPuzzle;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[0]);
    StandardParser parser = new StandardParser();
    StandardPuzzle standardPuzzle = parser.parse(input);
  }

}
