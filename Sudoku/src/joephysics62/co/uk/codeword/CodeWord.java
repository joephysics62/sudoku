package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeWord {

  private static final int THRESHOLD = 25;

  private final Dictionary _dictionary;

  private final CodeWordKey _key;

  private final Integer[][] _grid;
  private final List<Word> _horizontals;
  private final List<Word> _verticals;

  private final Integer _height;
  private final Integer _width;

  private interface GridGetter {
    Integer get(Integer[][] grid, int first, int second);
  }

  public CodeWord(final CodeWordKey key, final int height, final int width, final Integer[][] grid, final Dictionary dictionary) throws IOException, URISyntaxException {
    _key = key;
    _height = height;
    _width = width;
    _grid = grid;
    _dictionary = dictionary;
    _horizontals = findWords(new GridGetter() {
      @Override
      public Integer get(final Integer[][] grid, final int first, final int second) {
        return grid[first][second];
      }
    });
    _verticals = findWords(new GridGetter() {
      @Override
      public Integer get(final Integer[][] grid, final int first, final int second) {
        return grid[second][first];
      }
    });
  }

  private List<Word> findWords(final GridGetter gridGetter) {
    final List<Word> words = new ArrayList<>();
    for (int firstIndex = 0; firstIndex < _height; firstIndex++) {
      List<Integer> currentWord = null;
      for (int secondIndex = 0; secondIndex < _width; secondIndex++) {
        final Integer cell = gridGetter.get(_grid, firstIndex, secondIndex);
        if (cell != null) {
          if (currentWord == null) {
            currentWord = new ArrayList<Integer>();
          }
          currentWord.add(cell);
        }
        if (currentWord != null && (cell == null || secondIndex == _width - 1)) {
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
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (final Integer[] keys : _grid) {
      for (final Integer key : keys) {
        if (null == key) {
          sb.append("   ");
        }
        else if (_key.isSolved(key)) {
          sb.append("  " + _key.get(key));
        }
        else {
          sb.append(key < 10 ? "  " : " ");
          sb.append(key);
        }
      }
      sb.append(String.format("%n"));
    }
    return sb.toString();
  }

  public static CodeWord readFromFile(final String file) throws IOException, URISyntaxException {
    final List<String> allLines = Files.readAllLines(Paths.get(file));
    final CodeWordKey key = readKeyData(allLines.get(0));
    final Integer[][] grid = readGridData(allLines.subList(1, allLines.size()));
    return new CodeWord(key, 13, 13, grid, new Dictionary());
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

  private static Integer[][] readGridData(final List<String> gridData) {
    Integer[][] codeWordPuzzle = null;
    int rowIndex = 0;
    for (final String line : gridData) {
      final Integer[] row = Arrays.asList(line.split("\\|")).stream()
                                            .skip(1)
                                            .map(x -> toInt(x))
                                            .toArray(Integer[]::new);
      if (codeWordPuzzle == null) {
        codeWordPuzzle = new Integer[gridData.size()][row.length];
      }
      codeWordPuzzle[rowIndex++] = row;
    }
    return codeWordPuzzle;
  }

  private static Integer toInt(final String string) {
    final String trimmed = string.trim();
    if (trimmed.isEmpty()) {
      return null;
    }
    return Integer.valueOf(trimmed);
  }

  public CodeWord solve() throws Exception {
    final List<CodeWordKey> solutions = new ArrayList<>();
    recurse(_key, solutions);
    if (solutions.size() == 1) {
      return new CodeWord(solutions.get(0), _height, _width, _grid, _dictionary);
    }
    throw new RuntimeException();
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
