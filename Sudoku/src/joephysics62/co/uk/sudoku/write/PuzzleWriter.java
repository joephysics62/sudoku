package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleWriter {

  public void write(Puzzle puzzle, PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Cell cell : puzzle.getAllCells()) {
      final Coord coord = cell.getCoord();
      maxRow = Math.max(coord.getRow(), maxRow);
      maxCol = Math.max(coord.getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Cell cell : puzzle.getAllCells()) {
      final Coord coord = cell.getCoord();
      array[coord.getRow() - 1][coord.getCol() - 1] = convertToNiceValue(cell);
    }
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (j == 0) {
          out.print("|");
        }
        Object value = array[i][j];
        out.print(value == null ? "-|" : value + "|");
      }
      out.println();
    }
    out.println();
  }

  public static Integer convertToNiceValue(Cell cell) {
    int currentValues = cell.getCurrentValues();
    if (Integer.bitCount(currentValues) == 1) {
      return Integer.numberOfTrailingZeros(currentValues) + 1;
    }
    else {
      return null;
    }
  }
}
