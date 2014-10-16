package joephysics62.co.uk.sudoku.write;

import java.io.File;

import joephysics62.co.uk.sudoku.gridmaths.FourColourSolver;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.KillerSudokuReader;

import org.junit.Test;

public class TestHTMLKillerSudokuWriter {

  @Test
  public void testWrite() throws Exception {
    KillerSudokuReader reader = new KillerSudokuReader(Layout.CLASSIC_SUDOKU);
    Puzzle puzzle = reader.read(new File("examples/killer/times3942.html"));
    HTMLWriter htmlKillerSudokuWriter = new HTMLKillerSudokuWriter(puzzle, new FourColourSolver());
    htmlKillerSudokuWriter.write(new File("temp.html"));
  }

}
