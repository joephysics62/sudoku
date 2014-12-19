package joephysics62.co.uk.lsystems.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public abstract class ContextFreeLSystem implements TurtleLSystem {

  @Override
  public final List<Character> generate(final int iterations) {
    return recursiveReplace(axiom(), 0, iterations);
  }

  private List<Character> toCharList(final String string) {
    return string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
  }

  protected abstract String axiomString();

  protected abstract String applyRuleString(Character input);

  @Override
  public final List<Character> axiom() {
    return toCharList(axiomString());
  }

  @Override
  public final List<Character> applyRule(final Character input) {
    return toCharList(applyRuleString(input));
  }

  private List<Character> recursiveReplace(final List<Character> chars, final int iterations, final int limit) {
    if (iterations > limit || chars.size() > MAX_ELEMENTS) {
      return chars;
    }
    final List<Character> newChars = new ArrayList<Character>();
    for (final Character oldChar : chars) {
      newChars.addAll(applyRule(oldChar));
    }
    return recursiveReplace(newChars, iterations + 1, limit);
  }

}
