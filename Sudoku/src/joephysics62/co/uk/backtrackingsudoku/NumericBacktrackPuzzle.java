package joephysics62.co.uk.backtrackingsudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    solveInner(current, 0, solutions);
    return solutions;
  }

  private int[][] gridClone(final int[][] grid) {
    final int[][] clone = new int[_size][_size];
    for (int row = 0; row < _size; row++) {
      clone[row] = grid[row].clone();
    }
    return clone;
  }

  private int iterations = 0;

  private void solveInner(final int[][] current, final int cellNum, final List<int[][]> solutions) {
    iterations++;
    if (cellNum >= _size * _size) {
      System.out.println("FOUND ANSWER after " + iterations + " iterations");
      solutions.add(gridClone(current));
      return;
    }
    final int row = cellNum / _size;
    final int col = cellNum % _size;
    if (current[row][col] > 0) {
      // solved cell, continue;
      solveInner(current, cellNum + 1, solutions);
    }
    else {
      // not solved cell, try candidates
      for (int candidate = 1; candidate <= _size; candidate++) {
        if (isValidMove(candidate, row, col, current)) {
          current[row][col] = candidate;
          solveInner(current, cellNum + 1, solutions);
        }
      }
      for (int i = cellNum; i < _size * _size; i++) {
        final int row2 = i / _size;
        final int col2 = i % _size;
        current[row2][col2] = _puzzle[row2][col2];
      }
    }
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
