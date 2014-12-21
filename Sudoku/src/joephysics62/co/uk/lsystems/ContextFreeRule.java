package joephysics62.co.uk.lsystems;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContextFreeRule implements Rule<Character> {

  private final Character _match;
  private final List<Character> _replacement;

  private ContextFreeRule(final Character match, final List<Character> replacement) {
    _match = match;
    _replacement = Collections.unmodifiableList(replacement);
  }

  public static ContextFreeRule of(final Character match, final String replacement) {
    return new ContextFreeRule(match, replacement.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
  }

  @Override
  public boolean matches(final int index, final List<Character> input) {
    return _match.equals(input.get(index));
  }

  @Override
  public List<Character> replacement() {
    return _replacement;
  }

}
