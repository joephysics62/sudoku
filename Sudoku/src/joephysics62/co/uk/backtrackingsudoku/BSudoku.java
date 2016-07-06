package joephysics62.co.uk.backtrackingsudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class BSudoku {
  private static final int SUBGRID_SIZE = 3;
  private static int SIZE = 9;

  public static void main(final String[] args) throws IOException {
    final Path inputFile = Paths.get("examples", "sudoku", "classic", "times-7999");
    final int[][] puzzle = readPuzzle(inputFile);
    solve(puzzle);
  }

  private static void solve(final int[][] puzzle) {
    final int[][] answer = new int[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++) {
      answer[row] = puzzle[row].clone();
    }
    solveInner(puzzle, answer, 0);
  }

  private static int iterations = 0;

  private static void solveInner(final int[][] puzzle, final int[][] answer, final int cellNum) {
    iterations++;
    if (cellNum >= SIZE * SIZE) {
      System.out.println("FOUND ANSWER after " + iterations + " iterations");
      printGrid(answer);
      return;
    }
    final int row = cellNum / SIZE;
    final int col = cellNum % SIZE;
    if (answer[row][col] > 0) {
      // solved cell, continue;
      solveInner(puzzle, answer, cellNum + 1);
    }
    else {
      // not solved cell, try candidates
      for (int candidate = 1; candidate <= SIZE; candidate++) {
        if (isValidMove(candidate, row, col, answer)) {
          answer[row][col] = candidate;
          solveInner(puzzle, answer, cellNum + 1);
        }
      }
      for (int i = cellNum; i < SIZE * SIZE; i++) {
        final int row2 = i / SIZE;
        final int col2 = i % SIZE;
        answer[row2][col2] = puzzle[row2][col2];
      }
    }
  }

  private static boolean isValidMove(final int candidate, final int row, final int col, final int[][] answer) {
    for (int i = 0; i < SIZE; i++) {
      // compare row
      if (answer[row][i] == candidate) {
        return false;
      }
      // compare col
      if (answer[i][col] == candidate) {
        return false;
      }
    }
    final int subRowStart = row - row % SUBGRID_SIZE;
    final int subColStart = col - col % SUBGRID_SIZE;
    for (int subRow = subRowStart; subRow < subRowStart + SUBGRID_SIZE; subRow++) {
      for (int subCol = subColStart; subCol < subColStart + SUBGRID_SIZE; subCol++) {
        if (answer[subRow][subCol] == candidate) {
          return false;
        }
      }
    }
    return true;
  }

  private static void printGrid(final int[][] answer) {
    for (int i = 0; i < answer.length; i++) {
      System.out.println(Arrays.toString(answer[i]));
    }
    System.out.println();
  }

  private static int[][] readPuzzle(final Path inputFile) throws IOException {
    final List<String> lines = Files.readAllLines(inputFile);

    final int[][] puzzle = new int[SIZE][SIZE];

    for (int row = 0; row < SIZE; row++) {
      final String[] rowArray = lines.get(row).split("\\|");
      puzzle[row] = Arrays.stream(rowArray)
                          .skip(1)
                          .map(String::trim)
                          .mapToInt(BSudoku::toInt)
                          .toArray();
    }
    return puzzle;
  }

  private static int toInt(final String cellString) {
    if (cellString.isEmpty()) {
      return 0;
    }
    return Integer.valueOf(cellString);
  }
}
