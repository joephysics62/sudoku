package joephysics62.co.uk.lsystems;

import java.util.List;

public abstract class SingleCharacterRule implements Rule<Character> {
  private final Character _match;

  protected SingleCharacterRule(final Character match) {
    _match = match;
  }

  @Override
  public final boolean matches(final int index, final List<Character> input) {
    return _match.equals(input.get(index));
  }
}
