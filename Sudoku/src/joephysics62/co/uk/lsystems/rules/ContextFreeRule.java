package joephysics62.co.uk.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.lsystems.TurtleElement;

public abstract class ContextFreeRule implements Rule {
  private final Character _match;

  protected ContextFreeRule(final Character matchId) {
    _match = matchId;
  }

  @Override
  public final boolean matches(final int index, final List<TurtleElement> input) {
    return _match.equals(input.get(index).getId());
  }
}