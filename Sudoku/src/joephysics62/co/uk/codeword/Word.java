package joephysics62.co.uk.codeword;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Word {
  private final List<Integer> _cells;

  public Word(final List<Integer> cells) {
    _cells = Collections.unmodifiableList(cells);
  }

  public List<Integer> getCells() {
    return _cells;
  }

  public int size() {
    return _cells.size();
  }

  public boolean isSolved(final CodeWordKey key) {
    for (final Integer integer : _cells) {
      if (!key.isSolved(integer)) {
        return false;
      }
    }
    return true;
  }

  public String getRegex(final CodeWordKey key) {
    final String unknownsRegex = key.unknownsRegex();
    final StringBuilder sb = new StringBuilder();
    final Iterator<Integer> iterator = _cells.iterator();

    int unknownsCount = 0;
    while (iterator.hasNext()) {
      final Integer integer = iterator.next();
      final boolean isSolved = key.isSolved(integer);
      if (!isSolved) {
        unknownsCount++;
      }
      if (isSolved || !iterator.hasNext()) {
        if (unknownsCount > 0) {
          sb.append(unknownsRegex);
        }
        if (unknownsCount > 1) {
          sb.append("{" + unknownsCount + "}");
        }
      }
      if (isSolved) {
        unknownsCount = 0;
        sb.append(key.get(integer));
      }
    }
    return sb.toString();
  }

}
