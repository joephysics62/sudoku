package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodeWord {
  private final CodeWordGrid _grid;
  private final CodeWordKey _key;

  public CodeWord(final CodeWordGrid grid, final CodeWordKey key) {
    _grid = grid;
    _key = key;
  }

  public CodeWordGrid getGrid() {
    return _grid;
  }
  public CodeWordKey getKey() {
    return _key;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (final Integer[] keys : _grid.getGrid()) {
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

  public static CodeWord readFromFile(final String file) throws IOException {
    final List<String> allLines = Files.readAllLines(Paths.get(file));
    final CodeWordKey key = readKeyData(allLines.get(0));
    final CodeWordGrid codeWordPuzzle = readGridData(allLines.subList(1, allLines.size()));
    return new CodeWord(codeWordPuzzle, key);
  }

  private static CodeWordKey readKeyData(final String keyData) {
    if (keyData.trim().isEmpty()) {
      return new CodeWordKey();
    }
    final Map<Integer, Character> keyMap = new LinkedHashMap<>();
    for (final String keyPair : keyData.split(",")) {
      final String[] split = keyPair.split("=");
      final Integer key = toInt(split[0]);
      final Character value = Character.valueOf(split[1].trim().charAt(0));
      keyMap.put(key, value);
    }
    return new CodeWordKey(keyMap);
  }

  private static CodeWordGrid readGridData(final List<String> gridData) {
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
    return new CodeWordGrid(codeWordPuzzle);
  }

  private static Integer toInt(final String string) {
    final String trimmed = string.trim();
    if (trimmed.isEmpty()) {
      return null;
    }
    return Integer.valueOf(trimmed);
  }
}
