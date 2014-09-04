package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.MapBackedPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class FutoshikiBuilder implements PuzzleBuilder<Integer> {

  private final int _puzzleSize;

  public FutoshikiBuilder(final int puzzleSize) {
    _puzzleSize = puzzleSize;
  }

  @Override
  public Puzzle<Integer> read(final File input) throws IOException {
    String[][] stringTable = asTableOfStrings(input);

    final List<Integer> inits = new ArrayList<>();
    for (int i = 1; i <= _puzzleSize; i++) {
      inits.add(i);
    }
    MapBackedPuzzle<Integer> futoshiki = MapBackedPuzzle.forInits(inits);
    List<List<Integer>> givenValues = new ArrayList<>();
    for (String[] row : stringTable) {
      List<Integer> rowOut = new ArrayList<>();
      givenValues.add(rowOut);
      for (String cell : row) {
        String intValue = cell.replaceAll("[<>V^]", "").trim();
        rowOut.add(intValue.isEmpty() ? null : Integer.valueOf(intValue));
      }
    }
    futoshiki.addCells(givenValues);

    final Coord[][] wholePuzzle = new Coord[_puzzleSize][_puzzleSize];
    for (Cell<Integer> cell : futoshiki.getAllCells()) {
      Coord coord = cell.getIdentifier();
      wholePuzzle[coord.getRow() - 1][coord.getCol() - 1] = coord;
    }
    for (Coord[] row : wholePuzzle) {
      futoshiki.addConstraint(Uniqueness.<Integer>of(Arrays.asList(row)));
    }
    for (int col = 0; col < _puzzleSize; col++) {
      final List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < _puzzleSize; row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      futoshiki.addConstraint(Uniqueness.<Integer>of(colCells));
    }
    for (int row = 0; row < _puzzleSize; row++) {
      for (int col = 0; col < _puzzleSize; col++) {
        String cellString = stringTable[row][col];
        final Coord left = wholePuzzle[row][col];
        if (cellString.contains(">")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row][col + 1]));
        }
        if (cellString.contains("<")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row][col - 1]));
        }
        if (cellString.contains("V")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row + 1][col]));
        }
        if (cellString.contains("^")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row - 1][col]));
        }
      }
    }
    return futoshiki;
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
