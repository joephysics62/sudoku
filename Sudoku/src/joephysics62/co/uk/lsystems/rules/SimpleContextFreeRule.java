package joephysics62.co.uk.lsystems.rules;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.lsystems.Utils;

public class SimpleContextFreeRule extends ContextFreeRule<Character> {

  private final List<Character> _replacement;
  private final Character _match;

  private SimpleContextFreeRule(final Character match, final List<Character> replacement) {
    super(match);
    _match = match;
    _replacement = Collections.unmodifiableList(replacement);
  }

  public static SimpleContextFreeRule of(final Character match, final String replacement) {
    return new SimpleContextFreeRule(match, Utils.toChars(replacement));
  }

  @Override
  public List<Character> replacement() {
    return _replacement;
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", _match, Utils.toString(_replacement));
  }

}
