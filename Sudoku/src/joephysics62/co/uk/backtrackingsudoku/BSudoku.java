package joephysics62.co.uk.backtrackingsudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BSudoku {
  private final int[][] _puzzle;
  private final int _size;
  private final int _subsize;

  public BSudoku(final int[][] puzzle, final int size, final int subsize) {
    _puzzle = puzzle;
    _size = size;
    _subsize = subsize;
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
    final int subRowStart = row - row % _subsize;
    final int subColStart = col - col % _subsize;
    for (int subRow = subRowStart; subRow < subRowStart + _subsize; subRow++) {
      for (int subCol = subColStart; subCol < subColStart + _subsize; subCol++) {
        if (answer[subRow][subCol] == candidate) {
          return false;
        }
      }
    }
    return true;
  }

  public static BSudoku readPuzzle(final Path inputFile, final int size, final int subsize) throws IOException {
    final List<String> lines = Files.readAllLines(inputFile);

    final int[][] puzzle = new int[size][size];

    for (int row = 0; row < size; row++) {
      final String[] rowArray = lines.get(row).split("\\|");
      puzzle[row] = Arrays.stream(rowArray)
                          .skip(1)
                          .map(String::trim)
                          .mapToInt(BSudoku::toInt)
                          .toArray();
    }
    return new BSudoku(puzzle, size, subsize);
  }

  private static int toInt(final String cellString) {
    if (cellString.isEmpty()) {
      return 0;
    }
    return Integer.valueOf(cellString);
  }
}
