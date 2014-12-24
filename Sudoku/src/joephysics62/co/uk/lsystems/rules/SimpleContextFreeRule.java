package joephysics62.co.uk.lsystems.rules;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.lsystems.Utils;
import joephysics62.co.uk.lsystems.turtle.IModule;

public class SimpleContextFreeRule extends ContextFreeRule {

  private final List<IModule> _replacement;
  private final Character _match;

  public SimpleContextFreeRule(final Character match, final List<IModule> replacement) {
    super(match);
    _match = match;
    _replacement = Collections.unmodifiableList(replacement);
  }

  @Override
  public List<IModule> replacement(final double... x) {
    return _replacement;
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", _match, Utils.toString(_replacement));
  }

}
