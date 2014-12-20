package joephysics62.co.uk.lsystems;

import java.util.List;

public class ContextFreeRule implements Rule<Character> {

  private final Character _match;
  private final String _replacement;

  public ContextFreeRule(final Character match, final String replacement) {
    _match = match;
    _replacement = replacement;
  }

  @Override
  public boolean matches(final int index, final List<Character> input) {
    return _match.equals(input.get(index));
  }

  @Override
  public List<Character> replacement() {
    return null;
  }

}
