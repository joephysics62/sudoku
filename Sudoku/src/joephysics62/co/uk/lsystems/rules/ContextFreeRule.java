package joephysics62.co.uk.lsystems.rules;

import java.util.List;

public abstract class ContextFreeRule<T> implements Rule<T> {
  private final T _match;

  protected ContextFreeRule(final T match) {
    _match = match;
  }

  @Override
  public final boolean matches(final int index, final List<T> input) {
    return _match.equals(input.get(index));
  }
}
