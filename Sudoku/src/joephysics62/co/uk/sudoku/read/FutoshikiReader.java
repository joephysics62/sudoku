package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.sudoku.builder.FutoshikiBuilder;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class FutoshikiReader implements PuzzleReader {

  private final int _puzzleSize;

  public FutoshikiReader(final int puzzleSize) {
    _puzzleSize = puzzleSize;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    String[][] stringTable = asTableOfStrings(input);
    Integer[][] givenValues = new Integer[_puzzleSize][_puzzleSize];
    int rowIndex = 0;
    for (String[] row : stringTable) {
      int colIndex = 0;
      for (String cell : row) {
        String intValue = cell.replaceAll("[<>V^]", "").trim();
        givenValues[rowIndex][colIndex] = intValue.isEmpty() ? null : Integer.valueOf(intValue);
        colIndex++;
      }
      rowIndex++;
    }
    FutoshikiBuilder futoshikiBuilder = new FutoshikiBuilder(_puzzleSize);
    futoshikiBuilder.addCells(givenValues);

    for (int row = 0; row < _puzzleSize; row++) {
      for (int col = 0; col < _puzzleSize; col++) {
        String cellString = stringTable[row][col];
        if (cellString.contains(">")) {
          futoshikiBuilder.addGreaterThan(new Coord(row + 1, col + 1), new Coord(row + 1, col + 2));
        }
        if (cellString.contains("<")) {
          futoshikiBuilder.addGreaterThan(new Coord(row + 1, col + 1), new Coord(row + 1, col));
        }
        if (cellString.contains("V")) {
          futoshikiBuilder.addGreaterThan(new Coord(row + 1, col + 1), new Coord(row + 2, col + 1));
        }
        if (cellString.contains("^")) {
          futoshikiBuilder.addGreaterThan(new Coord(row + 1, col + 1), new Coord(row, col + 1));
        }
      }
    }
    return futoshikiBuilder.build();
  }

  private String[][] asTableOfStrings(final File input) throws IOException {
    List<String> allLines = Files.readAllLines(input.toPath(), Charset.forName("UTF-8"));
    if (allLines.size() != _puzzleSize) {
      throw new RuntimeException(allLines.size() + "");
    }
    String[][] stringTable = new String[_puzzleSize][_puzzleSize];
    int rowIndex = 0;
    for (String line : allLines) {
      String[] split = line.split("\\|");
      if (split.length != _puzzleSize + 1) {
        throw new RuntimeException(split.length + "");
      }
      stringTable[rowIndex++] = Arrays.copyOfRange(split, 1, _puzzleSize + 1);
    }
    return stringTable;
  }

}
