package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleWriter<T extends Comparable<T>> {

  private final Puzzle<T> _puzzle;
  public PuzzleWriter(Puzzle<T> puzzle) {
    _puzzle = puzzle;
  }
  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Cell<T> cell : _puzzle.getAllCells()) {
      final Coord coord = cell.getCoord();
      maxRow = Math.max(coord.getRow(), maxRow);
      maxCol = Math.max(coord.getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Cell<T> cell : _puzzle.getAllCells()) {
      final Coord coord = cell.getCoord();
      array[coord.getRow() - 1][coord.getCol() - 1] = cell.getCurrentValues().size() == 1 ? cell.getValue() : null;
    }
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (j == 0) {
          out.print("|");
        }
        Object value = array[i][j];
        out.print(value == null ? "-|" : value + "|");
      }
      out.println("");
    }
  }
}
