package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.puzzle.Puzzle2DImpl;
import joephysics62.co.uk.puzzle.PuzzleReader;
import joephysics62.co.uk.puzzle.PuzzleSolution;
import joephysics62.co.uk.puzzle.SolutionType;
import joephysics62.co.uk.xml.SvgBuilder;

public class CodeWord extends Puzzle2DImpl {

  private static final int THRESHOLD = 25;

  private final Dictionary _dictionary;

  private final CodeWordKey _key;
  private final CodeWordKey _startKey;

  private final Map<Coordinate, Integer> _grid;
  private final List<Word> _horizontals;
  private final List<Word> _verticals;


  public CodeWord(final CodeWordKey key, final CodeWordKey startKey, final int height, final int width, final Map<Coordinate, Integer> grid, final Dictionary dictionary) {
    super(height, width);
    _key = key;
    _startKey = startKey;
    _grid = grid;
    _dictionary = dictionary;
    _horizontals = findWords((a, b) -> Coordinate.of(a, b));
    _verticals = findWords((a, b) -> Coordinate.of(b, a));
  }

  private List<Word> findWords(final BiFunction<Integer, Integer, Coordinate> coordGetter) {
    final List<Word> words = new ArrayList<>();
    for (int firstIndex = 1; firstIndex <= _height; firstIndex++) {
      List<Integer> currentWord = null;
      for (int secondIndex = 1; secondIndex <= _width; secondIndex++) {
        final Integer cell = _grid.get(coordGetter.apply(firstIndex, secondIndex));
        if (cell != null) {
          if (currentWord == null) {
            currentWord = new ArrayList<Integer>();
          }
          currentWord.add(cell);
        }
        if (currentWord != null && (cell == null || secondIndex == _width)) {
          if (currentWord.size() > 1) {
            words.add(new Word(currentWord));
          }
          currentWord = null;
        }
      }
    }
    return words;
  }

  @Override
  protected String clueAt(final Coordinate coord) {
    final Integer integer = _grid.get(coord);
    if (integer == null) {
      return "   ";
    }
    if (integer < 10) {
      return "  " + integer;
    }
    return " " + integer.toString();
  }

  @Override
  protected String answerAt(final Coordinate coord) {
    final Integer integer = _grid.get(coord);
    final Character character = _key.get(integer);
    if (character == null) {
      return " ";
    }
    return character.toString();
  }

  @Override
  protected Path cssTemplate() {
    return Paths.get("templates", "hidato.css");
  }

  @Override
  protected void renderAnswer(final SvgBuilder svg, final Coordinate coord, final int cellSize) {
    renderGrid(svg, coord, cellSize,
               c -> Optional.ofNullable(_grid.get(c)).map(v -> _key.get(v)).map(Object::toString).orElse(null));
  }

  @Override
  protected void renderPuzzle(final SvgBuilder svg, final Coordinate coord, final int cellSize) {
    renderGrid(svg, coord, cellSize, c ->  Optional.ofNullable(_grid.get(c)).map(Object::toString).orElse(null));
    _startKey.render(svg, cellSize);
  }

  private void renderGrid(final SvgBuilder svg, final Coordinate coord, final int cellSize, final Function<Coordinate, String> cellString) {
    final int fontSize = 7 * cellSize / 12;
    if (_grid.containsKey(coord)) {
      final String cell = cellString.apply(coord);
      if (cell != null) {
        final int x = (coord.getCol() - 1) * cellSize + cellSize / 2;
        final int y = coord.getRow() * cellSize - (cellSize - fontSize) / 2;
        svg.addText(cell, x, y, fontSize);
      }
    }
    else {
      final int x = (coord.getCol() - 1) * cellSize;
      final int y = (coord.getRow() - 1) * cellSize;
      svg.addRectangle(cellSize, cellSize, x, y);
    }
  }

  public static CodeWord readFromFile(final String file) throws IOException, URISyntaxException {
    final List<String> allLines = Files.readAllLines(Paths.get(file));
    final CodeWordKey key = readKeyData(allLines.get(0));
    final List<String> dataRows = allLines.subList(1, allLines.size());
    final Map<Coordinate, Integer> grid = new LinkedHashMap<>();
    PuzzleReader.read(dataRows, (cell, coord) -> {
      final Integer intValue = toInt(cell);
      if (intValue != null) {
        grid.put(coord, intValue);
      }
    });
    final int height = dimFind(grid, Coordinate::getRow);
    final int width = dimFind(grid, Coordinate::getCol);
    return new CodeWord(key, key, height, width, grid, new Dictionary());
  }

  private static int dimFind(final Map<Coordinate, Integer> grid, final ToIntFunction<Coordinate> toIntFunction) {
    return grid.keySet().stream().mapToInt(toIntFunction).max().getAsInt();
  }

  private static CodeWordKey readKeyData(final String keyData) {
    if (keyData.trim().isEmpty()) {
      return new CodeWordKey();
    }
    final Map<Integer, Character> keyMap = Arrays.stream(keyData.split(","))
          .map(pair -> pair.split("="))
          .collect(Collectors.toMap(p -> toInt(p[0]),
                                    p-> Character.valueOf(p[1].trim().charAt(0))));
    return new CodeWordKey(keyMap);
  }

  private static Integer toInt(final String string) {
    final String trimmed = string.trim();
    if (trimmed.isEmpty()) {
      return null;
    }
    return Integer.valueOf(trimmed);
  }

  @Override
  public PuzzleSolution<CodeWord> solve() {
    final List<CodeWordKey> solutions = new ArrayList<>();
    recurse(_key, solutions);
    if (solutions.isEmpty()) {
      return new PuzzleSolution<CodeWord>(Optional.empty(), SolutionType.NONE);
    }
    final CodeWord solvedCodeword = new CodeWord(solutions.get(0), _startKey, _height, _width, _grid, _dictionary);
    return new PuzzleSolution<CodeWord>(Optional.of(solvedCodeword), solutions.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE);
  }

  private void recurse(final CodeWordKey currentKey, final List<CodeWordKey> solutions) {
    if (!isValid(currentKey)) {
      return;
    }
    if (currentKey.isSolved()) {
      solutions.add(currentKey);
      return;
    }
    final Map<Word, List<String>> mapz = new LinkedHashMap<>();

    for (final Word codeWordWord : getAllWords()) {
      if (codeWordWord.isSolved(currentKey)) {
        continue;
      }
      final List<String> matches = matches(codeWordWord, currentKey);
      if (null != matches) {
        mapz.put(codeWordWord, matches);
      }
    }
    if (mapz.isEmpty()) {
      System.err.println("Too many possibilies, not solvable");
      return;
    }

    final Set<Entry<Word, List<String>>> entrySet = mapz.entrySet();

    CodeWordKey basekey = currentKey;
    Word bestGuesser = entrySet.iterator().next().getKey();
    int bestSize = Integer.MAX_VALUE;
    for (final Entry<Word, List<String>> entry : entrySet) {
      final Word codeWordWord = entry.getKey();
      final List<String> matches = entry.getValue();
      if (matches.size() == 1) {
        if (basekey.isViableMatch(codeWordWord, matches.get(0))) {
          basekey = basekey.setWord(codeWordWord, matches.get(0));
        }
        else {
          return;
        }
      }
      else if (matches.size() > 1) {
        if (matches.size() < bestSize) {
          bestSize = matches.size();
          bestGuesser = codeWordWord;
        }
      }
    }
    final List<String> matches = mapz.get(bestGuesser);
    for (final String match : matches) {
      if (basekey.isViableMatch(bestGuesser, match)) {
        recurse(basekey.setWord(bestGuesser, match), solutions);
      }
    }

  }

  private List<Word> getAllWords() {
    final List<Word> allWords = new ArrayList<Word>();
    allWords.addAll(_horizontals);
    allWords.addAll(_verticals);
    return allWords;
  }

  private boolean isValid(final CodeWordKey newKey) {
    for (final Word word : getAllWords()) {
      final String setAnswer = word.getRegex(newKey);
      final List<String> matches = _dictionary.matches(setAnswer, word.size(), THRESHOLD);
      if (matches != null && matches.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  private List<String> matches(final Word word, final CodeWordKey key) {
    final String regex = word.getRegex(key);
    final List<String> matches = _dictionary.matches(regex, word.size(), THRESHOLD);
    if (matches.size() >= THRESHOLD) {
      return null;
    }
    return matches.stream().filter(x -> key.isViableMatch(word, x)).collect(Collectors.toList());
  }
}
