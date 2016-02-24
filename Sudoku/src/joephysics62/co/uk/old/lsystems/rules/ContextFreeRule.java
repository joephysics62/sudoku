package joephysics62.co.uk.old.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.old.lsystems.turtle.IModule;

public abstract class ContextFreeRule implements Rule {
  private final Character _match;

  protected ContextFreeRule(final Character matchId) {
    _match = matchId;
  }

  @Override
  public final boolean matches(final int index, final List<IModule> input) {
    return _match.equals(input.get(index).getId());
  }
}
