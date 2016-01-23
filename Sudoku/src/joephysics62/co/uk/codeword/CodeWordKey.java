package joephysics62.co.uk.codeword;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class CodeWordKey {

  private final BiMap<Integer, Character> _solution = HashBiMap.create();
  private final Set<Character> _remaining = new LinkedHashSet<>();

  private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  public CodeWordKey() {
    for (final char c : LETTERS) {
      _remaining.add(c);
    }
  }

  public CodeWordKey(final Map<Integer, Character> givens) {
    this();
    for (final Entry<Integer, Character> entry : givens.entrySet()) {
      addLetter(entry.getKey(), entry.getValue());
    }
  }

  public boolean isViableMatch(final CodeWordWord word, final String answer) {
    if (word.getCells().size() != answer.length()) {
      return false;
    }
    final Map<Integer, Character> newValues = new LinkedHashMap<>();
    for (int i = 0; i < answer.length(); i++) {
      final Integer intKey = word.getCells().get(i);
      final char charAt = answer.charAt(i);
      if (isSolved(intKey)) {
        if (get(intKey) != charAt) {
          return false;
        }
      }
      else {
        if (newValues.containsKey(intKey)) {
          if (charAt != newValues.get(intKey)) {
            return false;
          }
        }
        else {
          if (newValues.values().contains(charAt) || _solution.containsValue(charAt)) {
            return false;
          }
          newValues.put(intKey, charAt);
        }
      }
    }
    return true;
  }

  public CodeWordKey setWord(final CodeWordWord word, final String answer) {
    final CodeWordKey newKey = new CodeWordKey(new LinkedHashMap<Integer, Character>(_solution));
    for (int i = 0; i < answer.length(); i++) {
      final Integer intKey = word.getCells().get(i);
      if (!newKey.isSolved(intKey)) {
        newKey.addLetter(intKey, answer.charAt(i));
      }
    }
    return newKey;
  }

  public boolean isSolved() {
    return _remaining.isEmpty();
  }

  public boolean isSolved(final Integer key) {
    return _solution.containsKey(key);
  }

  public void addLetter(final Integer key, final Character value) {
    if (_solution.containsKey(key)) {
      throw new IllegalArgumentException("Already have letter for " + key);
    }
    _solution.put(key, value);
    _remaining.remove(value);
  }

  public Character get(final Integer key) {
    return _solution.get(key);
  }

  public Set<Character> getRemaining() {
    return Collections.unmodifiableSet(_remaining);
  }

  public String unknownsRegex() {
    final StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (final Character character : _remaining) {
      sb.append(character);
    }
    sb.append(']');
    return sb.toString();
  }

  @Override
  public String toString() {
    return _solution.toString();
  }

}
