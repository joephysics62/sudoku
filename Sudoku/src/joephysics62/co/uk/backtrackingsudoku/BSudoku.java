package joephysics62.co.uk.backtrackingsudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class BSudoku extends NumericBacktrackPuzzle {
  private final int _subsize;

  public BSudoku(final int[][] puzzle, final int size, final int subsize) {
    super(puzzle, size);
    _subsize = subsize;
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
