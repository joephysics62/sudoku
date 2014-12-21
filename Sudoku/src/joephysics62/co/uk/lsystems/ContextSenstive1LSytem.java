package joephysics62.co.uk.lsystems;

import java.util.List;
import java.util.Optional;

public abstract class ContextSenstive1LSytem extends CharacterLSystem {

  protected abstract String applyContextSensitiveRule(final Character input, final Optional<Character> preceding, final Optional<Character> successor);

  private Optional<Character> successor(final int index, final List<Character> current) {
    for (int i = index + 1; i < current.size(); i++) {
      if (!contextIgnorable().contains(current.get(i))) {
        return Optional.of(current.get(i));
      }
    }
    return Optional.empty();
  }

  private Optional<Character> preceding(final int index, final List<Character> current) {
    for (int i = index - 1; i >= 0; i--) {
      if (!contextIgnorable().contains(current.get(i))) {
        return Optional.of(current.get(i));
      }
    }
    return Optional.empty();
  }

  protected abstract List<Character> contextIgnorable();

  protected abstract Character pushChar();
  protected abstract Character popChar();

}
