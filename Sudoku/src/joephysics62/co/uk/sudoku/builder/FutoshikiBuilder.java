package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class FutoshikiBuilder implements ArrayPuzzleBuilder {
  private Integer[][] _givenCells;
  private final List<GreaterThan> _greaterThanConstraints = new ArrayList<>();
  private String _title;
  private final PuzzleLayout _layout;

  public FutoshikiBuilder(final PuzzleLayout layout) {
    _layout = layout;
    if (_layout.hasSubtables()) {
      throw new IllegalArgumentException("Futoshikis do not have subtables");
    }
  }

  @Override
  public void addGiven(Integer value, Coord coord) {
    if (null == _givenCells) {
      _givenCells = new Integer[_layout.getHeight()][_layout.getWidth()];
    }
    _givenCells[coord.getRow() - 1][coord.getCol() -1] = value;
  }

  @Override
  public void addTitle(final String title) {
    _title = title;
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle futoshiki = ArrayPuzzle.forPossiblesSize(_title, _layout);
    int height = _layout.getHeight();
    int width = _layout.getWidth();
    final Coord[][] wholePuzzle = new Coord[height][width];
    for (int row = 1; row <= height; row++) {
      for (int col = 1; col <= width; col++) {
        wholePuzzle[row - 1][col - 1] = new Coord(row, col);
      }
    }
    for (Coord[] row : wholePuzzle) {
      futoshiki.addConstraint(Uniqueness.of(Arrays.asList(row)));
    }
    for (int col = 0; col < height; col++) {
      final List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < width; row++) {
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
