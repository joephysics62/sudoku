package joephysics62.co.uk.lsystems.rules;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.Utils;

public class SimpleContextFreeRule extends ContextFreeRule {

  private final List<TurtleElement> _replacement;
  private final Character _match;

  public SimpleContextFreeRule(final Character match, final List<TurtleElement> replacement) {
    super(match);
    _match = match;
    _replacement = Collections.unmodifiableList(replacement);
  }

  @Override
  public List<TurtleElement> replacement(final double... x) {
    return _replacement;
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", _match, Utils.toString(_replacement));
  }

}
