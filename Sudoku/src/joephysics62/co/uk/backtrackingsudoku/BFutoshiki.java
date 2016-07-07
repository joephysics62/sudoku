package joephysics62.co.uk.backtrackingsudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.grid.Coordinate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class BFutoshiki extends NumericBacktrackPuzzle {
  private final SetMultimap<Coordinate, Coordinate> _gtConstraints;
  private final SetMultimap<Coordinate, Coordinate> _ltConstraints;

  public static void main(final String[] args) throws IOException {
    final BFutoshiki bFutoshiki = readFile(Paths.get("examples", "futoshiki", "5by5", "times2601.txt"));
    final List<int[][]> solve = bFutoshiki.solve();
    for (final int[][] is : solve) {
      bFutoshiki.printGrid(is);
    }
  }

  public static BFutoshiki readFile(final Path inputFile) throws IOException {
    final List<String> lines = Files.readAllLines(inputFile);
    final int size = lines.size() / 2 + 1;
    final int[][] puzzle = new int[size][size];

    final HashMultimap<Coordinate, Coordinate> constraints = HashMultimap.<Coordinate, Coordinate>create();

    for (int row = 0; row < lines.size(); row++) {
      final String[] rowArray = Arrays.copyOfRange(lines.get(row).split("\\|"), 1, lines.size() + 1);
      for (int col = 0; col < lines.size(); col++) {
        if (row % 2 == 0 && col % 2 == 0) {
          puzzle[row / 2][col / 2] = rowArray[col].trim().isEmpty() ? 0 : Integer.valueOf(rowArray[col]);
        }
        else if (row % 2 != col % 2) {
          final Coordinate start = Coordinate.of(row / 2, col / 2);
          switch (rowArray[col].trim()) {
          case ">":
            constraints.put(start, start.right());
            break;
          case "<":
            constraints.put(start.right(), start);
            break;
          case "V":
            constraints.put(start, start.down());
            break;
          case "^":
            constraints.put(start.down(), start);
            break;
          default:
            break;
          }
        }
      }
    }
    return new BFutoshiki(puzzle, constraints, size);
  }

  public BFutoshiki(final int[][] puzzle, final SetMultimap<Coordinate, Coordinate> constraints, final int size) {
    super(puzzle, size);
    _gtConstraints = constraints;
    _ltConstraints = Multimaps.invertFrom(_gtConstraints, HashMultimap.<Coordinate, Coordinate>create());
  }

  @Override
  protected boolean isValidMove(final int candidate, final int row, final int col, final int[][] answer) {
    for (int i = 0; i < getSize(); i++) {
      // compare row
      if (answer[row][i] == candidate) {
        return false;
      }
      // compare col
      if (answer[i][col] == candidate) {
        return false;
      }
    }
    final Coordinate asCoord = Coordinate.of(row, col);
    for (final Coordinate lesserCoord : _gtConstraints.get(asCoord)) {
      final int otherVal = answer[lesserCoord.getRow()][lesserCoord.getCol()];
      if (otherVal > 0 && candidate < otherVal) {
        return false;
      }
    }
    for (final Coordinate greaterCoord : _ltConstraints.get(asCoord)) {
      final int otherVal = answer[greaterCoord.getRow()][greaterCoord.getCol()];
      if (otherVal > 0 && candidate > otherVal) {
        return false;
      }
    }
    return true;
  }

}
