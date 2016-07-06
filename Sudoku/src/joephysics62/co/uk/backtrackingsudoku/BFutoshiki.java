package joephysics62.co.uk.backtrackingsudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.grid.Coordinate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class BFutoshiki {
  private final int[][] _puzzle;
  private final int _size;
  private final SetMultimap<Coordinate, Coordinate> _gtConstraints;
  private final SetMultimap<Coordinate, Coordinate> _ltConstraints;

  public static void main(final String[] args) {
    final int size = 5;
    final int[][] puzzle =
        new int[][] {{0, 0, 0, 0 ,0},
                     {0, 2, 0, 0, 0},
                     {0, 0, 0, 1, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0}};

    final HashMultimap<Coordinate, Coordinate> constraints = HashMultimap.<Coordinate, Coordinate>create();
    constraints.put(Coordinate.of(0, 0), Coordinate.of(0, 1));
    constraints.put(Coordinate.of(0, 2), Coordinate.of(1, 2));
    constraints.put(Coordinate.of(1, 1), Coordinate.of(1, 0));
    constraints.put(Coordinate.of(1, 4), Coordinate.of(2, 4));
    constraints.put(Coordinate.of(3, 2), Coordinate.of(4, 2));
    constraints.put(Coordinate.of(3, 4), Coordinate.of(3, 3));
    constraints.put(Coordinate.of(4, 0), Coordinate.of(4, 1));

    final List<int[][]> solve = new BFutoshiki(puzzle, constraints, size).solve();
    for (final int[][] is : solve) {
      printGrid(is);
    }
  }

  private static void printGrid(final int[][] answer) {
    for (int i = 0; i < answer.length; i++) {
      System.out.println(Arrays.toString(answer[i]));
    }
    System.out.println();
  }

  public BFutoshiki(final int[][] puzzle, final SetMultimap<Coordinate, Coordinate> constraints, final int size) {
    _puzzle = puzzle;
    _gtConstraints = constraints;
    _ltConstraints = Multimaps.invertFrom(_gtConstraints, HashMultimap.<Coordinate, Coordinate>create());
    _size = size;
  }

  public List<int[][]> solve() {
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

  private boolean isValidMove(final int candidate, final int row, final int col, final int[][] answer) {
    for (int i = 0; i < _size; i++) {
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
