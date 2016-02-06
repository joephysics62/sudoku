package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class CodewordSolver {
  private static final String FILE = "examples\\codeword\\codeWordTimes2623.csv";

  private static final int THRESHOLD = 25;

  public static void main(final String[] args) throws IOException, URISyntaxException {
    final String file = FILE;
    final CodeWord puzzle = CodeWord.readFromFile(file);
    final CodewordSolver codewordSolver = new CodewordSolver();
    codewordSolver.solve(puzzle);
  }

  private final Dictionary _dictionary;

  public CodewordSolver() throws IOException, URISyntaxException {
    _dictionary = new Dictionary();
  }

  public void solve(final CodeWord puzzle) {
    final long start = System.currentTimeMillis();
    recurse(puzzle.getGrid(), puzzle.getKey());
    System.out.println("Time taken " + (System.currentTimeMillis() - start) + " ms");
  }

  private void recurse(final CodeWordGrid grid, final CodeWordKey currentKey) {
    if (!isValid(grid, currentKey)) {
      return;
    }
    if (currentKey.isSolved()) {
      System.out.println("SOLVED!!!");
      System.out.println(currentKey);
      System.out.println(new CodeWord(grid, currentKey));
      return;
    }
    final Map<CodeWordWord, List<String>> mapz = new LinkedHashMap<>();
    for (final CodeWordWord codeWordWord : grid.getAllWords()) {
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

    final Set<Entry<CodeWordWord, List<String>>> entrySet = mapz.entrySet();

    CodeWordKey basekey = currentKey;
    CodeWordWord bestGuesser = entrySet.iterator().next().getKey();
    int bestSize = Integer.MAX_VALUE;
    for (final Entry<CodeWordWord, List<String>> entry : entrySet) {
      final CodeWordWord codeWordWord = entry.getKey();
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
        recurse(grid, basekey.setWord(bestGuesser, match));
      }
    }

  }

  private boolean isValid(final CodeWordGrid grid, final CodeWordKey newKey) {
    for (final CodeWordWord word : grid.getAllWords()) {
      final String setAnswer = word.getRegex(newKey);
      final List<String> matches = _dictionary.matches(setAnswer, word.size(), THRESHOLD);
      if (matches != null && matches.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  private List<String> matches(final CodeWordWord word, final CodeWordKey key) {
    final String regex = word.getRegex(key);
    final List<String> matches = _dictionary.matches(regex, word.size(), THRESHOLD);
    if (matches.size() >= THRESHOLD) {
      return null;
    }
    return matches.stream().filter(x -> key.isViableMatch(word, x)).collect(Collectors.toList());

  }


}
