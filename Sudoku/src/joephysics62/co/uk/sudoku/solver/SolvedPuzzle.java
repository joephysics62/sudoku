package joephysics62.co.uk.sudoku.solver;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Coord;

public class SolvedPuzzle {
  private final Map<Coord, Integer> _valueMap;

  public SolvedPuzzle(final Map<Coord, Integer> valueMap) {
    _valueMap = valueMap;
  }

  public Integer getValue(final Coord cellId) {
    return _valueMap.get(cellId);
  }

  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    Set<Coord> keySet = _valueMap.keySet();
    for (Coord coord : keySet) {
      maxRow = Math.max(coord.getRow(), maxRow);
      maxCol = Math.max(coord.getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Entry<Coord, Integer> entry : _valueMap.entrySet()) {
      Coord coord = entry.getKey();
      array[coord.getRow() - 1][coord.getCol() - 1] = entry.getValue();
    }
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (j == 0) {
          out.print("|");
        }
        Object value = array[i][j];
        out.print(value == null ? " " : value + "|");
      }
      out.println("");
    }
  }
}
