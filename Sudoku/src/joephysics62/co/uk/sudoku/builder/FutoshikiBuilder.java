package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class FutoshikiBuilder implements ArrayPuzzleBuilder {

  private final int _puzzleSize;
  private Integer[][] _givenCells;
  private final List<GreaterThan> _greaterThanConstraints = new ArrayList<>();

  public FutoshikiBuilder(final int puzzleSize) {
    _puzzleSize = puzzleSize;
  }

  @Override
  public void addGivens(Integer[][] givenCells) {
    _givenCells = givenCells;
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle futoshiki = ArrayPuzzle.forPossiblesSize(_puzzleSize);
    final Coord[][] wholePuzzle = new Coord[_puzzleSize][_puzzleSize];
    for (int row = 1; row <= _puzzleSize; row++) {
      for (int col = 1; col <= _puzzleSize; col++) {
        wholePuzzle[row - 1][col - 1] = new Coord(row, col);
      }
    }
    for (Coord[] row : wholePuzzle) {
      futoshiki.addConstraint(Uniqueness.of(Arrays.asList(row)));
    }
    for (int col = 0; col < _puzzleSize; col++) {
      final List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < _puzzleSize; row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      futoshiki.addConstraint(Uniqueness.of(colCells));
    }
    if (_givenCells != null) {
      futoshiki.addCells(_givenCells);
    }
    for (GreaterThan gtConstraint : _greaterThanConstraints) {
      futoshiki.addConstraint(gtConstraint);
    }
    return futoshiki;
  }

  public void addGreaterThan(Coord left, Coord right) {
    _greaterThanConstraints.add(GreaterThan.of(left, right));
  }

}
