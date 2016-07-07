package joephysics62.co.uk.sudoku;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.grid.Coordinate;

public class KenKen extends NumericBacktrackPuzzle {

  private final Map<Coordinate, ArithmeticGroup> _groupLookup;

  public KenKen(final int[][] puzzle, final int size, final List<ArithmeticGroup> groups) {
    super(puzzle, size);
    _groupLookup = new LinkedHashMap<>();
    for (final ArithmeticGroup kenKenGroup : groups) {
      for (final Coordinate coord: kenKenGroup.getCells())  {
        _groupLookup.put(coord, kenKenGroup);
      }
    }
  }

  @Override
  protected boolean isValidMove(final int candidate, final int row, final int col, final int[][] answer) {
    for (int i = 0; i < getSize(); i++) {
      if (answer[row][i] == candidate) {
        return false;
      }
      if (answer[i][col] == candidate) {
        return false;
      }
    }
    final Coordinate candidateCoord = Coordinate.of(row, col);

    final ArithmeticGroup kenKenGroup = _groupLookup.get(candidateCoord);

    int runningTarget = candidate;
    final int target = kenKenGroup.getTarget();
    for (final Coordinate coord : kenKenGroup.getCells()) {
      if (coord.equals(candidateCoord)) {
        continue;
      }
      final int otherVal = answer[coord.getRow()][coord.getCol()];
      if (otherVal == 0) {
        return true;
      }
      switch (kenKenGroup.getOp()) {
      case SUBTRACT:
        return candidate - otherVal == target || otherVal - candidate == target;
      case DIVIDE:
        return candidate / otherVal == target || otherVal / candidate == target;
      case ADD:
        runningTarget += otherVal;
        break;
      case MULTIPLY:
        runningTarget *= otherVal;
        break;
      default:
        throw new UnsupportedOperationException();
      }
    }
    return runningTarget == target;
  }

}
