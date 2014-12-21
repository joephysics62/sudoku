package joephysics62.co.uk.lsystems.rules;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleContextFreeRule extends ContextFreeRule<Character> {

  private final List<Character> _replacement;

  private SimpleContextFreeRule(final Character match, final List<Character> replacement) {
    super(match);
    _replacement = Collections.unmodifiableList(replacement);
  }

  public static SimpleContextFreeRule of(final Character match, final String replacement) {
    return new SimpleContextFreeRule(match, replacement.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
  }

  @Override
  public List<Character> replacement() {
    return _replacement;
  }

}
