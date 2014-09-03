package joephysics62.co.uk.sudoku.parse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import joephysics62.co.uk.sudoku.futoshiki.Futoshiki;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.GreaterThan;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Uniqueness;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;

public class FutoshikiParser {
  public Puzzle<Integer> parse(final File input, final int puzzleSize) throws IOException {
    String[][] stringTable = asTableOfStrings(input, puzzleSize);


    Futoshiki<Integer> futoshiki = new Futoshiki<Integer>(new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5)));
    List<List<Integer>> givenValues = new ArrayList<>();


    for (int row = 0; row < puzzleSize; row++) {
      for (int col = 0; col < puzzleSize; col++) {

      }
    }
    for (String[] row : stringTable) {
      List<Integer> rowOut = new ArrayList<>();
      givenValues.add(rowOut);
      for (String cell : row) {
        String intValue = cell.replaceAll("[<>V^]", "").trim();
        rowOut.add(intValue.isEmpty() ? null : Integer.valueOf(intValue));
      }
    }
    futoshiki.addCells(givenValues);

    final Coord[][] wholePuzzle = new Coord[puzzleSize][puzzleSize];
    for (Cell<Integer> cell : futoshiki.getAllCells()) {
      Coord coord = cell.getIdentifier();
      wholePuzzle[coord.getRow() - 1][coord.getCol() - 1] = coord;
    }
    for (Coord[] row : wholePuzzle) {
      futoshiki.addConstraint(Uniqueness.<Integer>of(Arrays.asList(row)));
    }
    for (int col = 0; col < puzzleSize; col++) {
      final List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < puzzleSize; row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      futoshiki.addConstraint(Uniqueness.<Integer>of(colCells));
    }
    for (int row = 0; row < puzzleSize; row++) {
      for (int col = 0; col < puzzleSize; col++) {
        String cellString = stringTable[row][col];
        final Coord left = wholePuzzle[row][col];
        if (cellString.contains(">")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row][col + 1]));
        }
        else if (cellString.contains("<")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row][col - 1]));
        }
        else if (cellString.contains("V")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row + 1][col]));
        }
        else if (cellString.contains("^")) {
          futoshiki.addConstraint(GreaterThan.<Integer>of(left, wholePuzzle[row - 1][col]));
        }
      }
    }
    return futoshiki;
  }

  private String[][] asTableOfStrings(final File input, final int puzzleSize) throws IOException {
    List<String> allLines = Files.readAllLines(input.toPath(), Charset.forName("UTF-8"));
    if (allLines.size() != puzzleSize) {
      throw new RuntimeException(allLines.size() + "");
    }
    String[][] stringTable = new String[puzzleSize][puzzleSize];
    int rowIndex = 0;
    for (String line : allLines) {
      String[] split = line.split("\\|");
      if (split.length != puzzleSize + 1) {
        throw new RuntimeException(split.length + "");
      }
      stringTable[rowIndex++] = Arrays.copyOfRange(split, 1, puzzleSize + 1);
    }
    return stringTable;
  }

  public static final void main(final String args[]) throws IOException {
    Puzzle<Integer> puzzle = new FutoshikiParser().parse(new File("examples/futoshiki/5by5/times2198.txt"), 5);
    PuzzleSolver<Integer> solver = new PuzzleSolver<Integer>();
    SolutionResult<Integer> result = solver.solve(puzzle);
    if (result.getType() == SolutionType.UNIQUE) {
      result.getSolution().write(System.out);
    }
    System.out.println("Solution took " + result.getTiming() + "ms");
  }

}
