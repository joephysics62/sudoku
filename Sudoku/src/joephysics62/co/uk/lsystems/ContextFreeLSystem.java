package joephysics62.co.uk.lsystems;

import java.util.List;

public abstract class ContextFreeLSystem extends CharacterLSystem {
  protected abstract String applyRuleString(Character input);

  @Override
  public final List<Character> applyRule(final int index, final List<Character> input) {
    return toCharList(applyRuleString(input.get(index)));
  }
}
