package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class CodewordSolver {

  private static final int THRESHOLD = 25;

  private final Dictionary _dictionary;

  public CodewordSolver() throws IOException, URISyntaxException {
    _dictionary = new Dictionary();
  }

  public List<CodeWord> solve(final CodeWord puzzle) {
    final List<CodeWord> solutions = new ArrayList<>();
    recurse(puzzle.getGrid(), puzzle.getKey(), solutions);
    return solutions;
  }

  private void recurse(final CodeWordGrid grid, final CodeWordKey currentKey, final List<CodeWord> solutions) {
    if (!isValid(grid, currentKey)) {
      return;
    }
    if (currentKey.isSolved()) {
      solutions.add(new CodeWord(grid, currentKey));
      return;
    }
    final Map<Word, List<String>> mapz = new LinkedHashMap<>();

    for (final Word codeWordWord : grid.getAllWords()) {
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
        recurse(grid, basekey.setWord(bestGuesser, match), solutions);
      }
    }

  }

  private boolean isValid(final CodeWordGrid grid, final CodeWordKey newKey) {
    for (final Word word : grid.getAllWords()) {
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
