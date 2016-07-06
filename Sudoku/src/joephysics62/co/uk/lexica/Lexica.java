package joephysics62.co.uk.lexica;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.grid.Coordinate;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class Lexica {

  private final List<WordLexica> _words;
  private final ListMultimap<Integer, Character> _rowClues;
  private final ListMultimap<Integer, Character> _colClues;

  final Map<Coordinate, Integer> rowAnswers = new LinkedHashMap<>();
  final Map<Coordinate, Integer> colAnswers = new LinkedHashMap<>();

  public Lexica(final List<WordLexica> words, final ListMultimap<Integer, Character> rowClues, final ListMultimap<Integer, Character> colClues) {
    _words = words;
    _rowClues = rowClues;
    _colClues = colClues;
  }

  public void solve() throws URISyntaxException, IOException {
    final List<String> wordList = Files.readAllLines(Paths.get(getClass().getResource("/sowpods.txt").toURI()))
      .stream()
      .map(String::toUpperCase)
      .collect(Collectors.toList());

    solveInner(0, wordList);
  }

  private void solveInner(final int wordIndex, final List<String> wordList) {
    if (wordIndex >= _words.size()) {
      System.out.println("IS SOLVED!!");
      return;
    }
    final WordLexica wordLexica = _words.get(wordIndex);

    final List<Coordinate> cells = wordLexica.getCells();
    // for move in validMove
    //    setValidMove for wordIndex
    //    solveInner(wordIndex + 1)

    // if validMove empty
    //  unset move for wordIndex - 1 (if wordIdex > 0)
  }

  public static void main(final String[] args) throws IOException, URISyntaxException {
    final Path inputFile = Paths.get("examples", "lexica", "times-3071.txt");
    final Lexica lexica = readFile(inputFile);

    lexica.solve();
  }

  private static Lexica readFile(final Path inputFile) throws IOException, URISyntaxException {
    final List<String> puzzleLines = Files.readAllLines(inputFile);

    final int rawHeight = puzzleLines.size();
    final int rawWidth = puzzleLines.get(0).split("\\|").length;
    final char[][] grid = new char[rawHeight][rawWidth];
    for (int row = 0; row < rawHeight; row++) {
      final String[] rowArray = puzzleLines.get(row).split("\\|");
      for (int col = 0; col < rawWidth; col++) {
        grid[row][col] = rowArray[col].charAt(0);
      }
    }

    // Build row clues
    final ListMultimap<Integer, Character> rowClues = buildRowClues(rawHeight, grid);

    // Build col clues
    final ListMultimap<Integer, Character> colClues = buildColClues(rawWidth, grid);

    final List<WordLexica> words = new ArrayList<>();

    // do across words
    for (int row = 1; row < rawHeight - 1; row++) {
      List<Coordinate> current = null;
      for (int col = 1; col < rawWidth - 1; col++) {
        final char innerVal = grid[row][col];
        if (innerVal == ' ') {
          if (current == null) {
            current = new ArrayList<>();
          }
          current.add(Coordinate.of(row, col));
        }
        if (innerVal == '/' || col == rawWidth - 2) {
          if (current != null && current.size() > 1) {
            words.add(new WordLexica(current));
          }
          current = null;
        }
      }
    }

    // do down words
    for (int col = 1; col < rawWidth - 1; col++) {
      List<Coordinate> current = null;
      for (int row = 1; row < rawHeight - 1; row++) {
        final char innerVal = grid[row][col];
        if (innerVal == ' ') {
          if (current == null) {
            current = new ArrayList<>();
          }
          current.add(Coordinate.of(row, col));
        }
        if (innerVal == '/' || row == rawHeight - 2) {
          if (current != null && current.size() > 1) {
            words.add(new WordLexica(current));
          }
          current = null;
        }
      }
    }
    return new Lexica(words, rowClues, colClues);
  }

  private static ListMultimap<Integer, Character> buildRowClues(final int rawHeight, final char[][] grid) {
    final ListMultimap<Integer, Character> rowClues = ArrayListMultimap.create();
    for (int row = 1; row < rawHeight - 1; row++) {
      addIfLetter(rowClues, row, grid[row][0]);
      addIfLetter(rowClues, row, grid[row][rawHeight - 1]);
    }
    return rowClues;
  }


  private static ListMultimap<Integer, Character> buildColClues(final int rawWidth, final char[][] grid) {
    final ListMultimap<Integer, Character> colClues = ArrayListMultimap.create();
    for (int col = 1; col < rawWidth - 1; col++) {
      addIfLetter(colClues, col, grid[0][col]);
      addIfLetter(colClues, col, grid[rawWidth - 1][col]);
    }
    return colClues;
  }


  private static void addIfLetter(final ListMultimap<Integer, Character> clueDef, final int index, final char charr) {
    if (charr != '-') {
      clueDef.put(index, charr);
    }
  }

}
