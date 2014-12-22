package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public abstract class CharacterLSystem implements TurtleLSystem<Character> {

  @Override
  public final List<Character> generate(final int iterations) {
    return recursiveReplace(axiom(), 0, iterations);
  }

  protected static List<Character> toCharList(final String string) {
    return string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
  }

  protected abstract String axiomString();

  @Override
  public final List<Character> axiom() {
    return toCharList(axiomString());
  }

  private List<Character> recursiveReplace(final List<Character> chars, final int iterations, final int limit) {
    if (iterations >= limit || chars.size() > MAX_ELEMENTS) {
      return chars;
    }
    final List<Character> newChars = new ArrayList<Character>();
    for (int index = 0; index < chars.size(); index++) {
      newChars.addAll(findAndApply(index, chars));
    }
    return recursiveReplace(newChars, iterations + 1, limit);
  }

  private List<Character> findAndApply(final int index, final List<Character> chars) {
    for (final Rule<Character> rule : rules()) {
      if (rule.matches(index, chars)) {
        return rule.replacement();
      }
    }
    return Collections.singletonList(chars.get(index));
  }

}
