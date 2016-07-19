package joephysics62.co.uk.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import joephysics62.co.uk.grid.Coordinate;

public abstract class NumericBacktrackPuzzle {

  private final int[][] _puzzle;
  private final int _size;

  public NumericBacktrackPuzzle(final int[][] puzzle, final int size) {
    _puzzle = puzzle;
    _size = size;
  }

  public final List<int[][]> solve() {
    final int[][] current = gridClone(_puzzle);
    final List<int[][]> solutions = new ArrayList<>();
    final Stack<Coordinate> coords = new Stack<Coordinate>();

    boolean reachedEnd = false;
    Coordinate curr = findBestUnsolved(current);
    while (!reachedEnd && solutions.size() < 2) {
      final int row = curr.getRow();
      final int col = curr.getCol();
      final int val = current[row][col];
      //System.out.println("Candidate = " + (val + 1));
      boolean seenValid = false;
      for (int candidate = val + 1; candidate <= _size; candidate++) {
        if (isValidMove(candidate, row, col, current)) {
          current[row][col] = candidate;
          coords.push(curr);
          curr = findBestUnsolved(current);
          seenValid = true;
          if (curr == null) {
            solutions.add(gridClone(current));
          }
          break;
        }
      }
      if (!seenValid || curr == null) {
        if (curr != null) {
          current[row][col] = _puzzle[row][col];
        }
        curr = coords.pop();
        if (coords.isEmpty()) {
          reachedEnd = true;
        }
      }

    }
    return solutions;
  }

  private Coordinate findBestUnsolved(final int[][] current) {
    int bestCount = Integer.MAX_VALUE;
    Coordinate best = null;
    for (int row = 0; row < _size; row++) {
      for (int col = 0; col < _size; col++) {
        if (current[row][col] > 0) {
          continue;
        }
        final int currCount = moveCount(current, row, col);
        if (currCount < bestCount) {
          bestCount = currCount;
          best = Coordinate.of(row, col);
          if (bestCount < 2) {
            return best;
          }
        }
      }
    }
    return best;
  }

  private int moveCount(final int[][] current, final int row, final int col) {
    int currCount = 0;
    for (int i = 1; i <= _size; i++) {
      if (isValidMove(i, row, col, current)) {
        currCount++;
      }
    }
    return currCount;
  }

  private int[][] gridClone(final int[][] grid) {
    final int[][] clone = new int[_size][_size];
    for (int row = 0; row < _size; row++) {
      clone[row] = grid[row].clone();
    }
    return clone;
  }

  protected void printGrid(final int[][] answer) {
    for (int i = 0; i < answer.length; i++) {
      System.out.println(Arrays.toString(answer[i]));
    }
    System.out.println();
  }

  protected int getSize() {
    return _size;
  }

  protected abstract boolean isValidMove(final int candidate, final int row, final int col, final int[][] current);
}
