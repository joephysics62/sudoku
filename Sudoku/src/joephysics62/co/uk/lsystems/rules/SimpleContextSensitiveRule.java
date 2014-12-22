package joephysics62.co.uk.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.lsystems.Utils;

public class SimpleContextSensitiveRule extends ContextSensitiveRule<Character> {

  private final Character _predecessor;
  private final Character _successor;
  private final List<Character> _replacement;
  private final List<Character> _ignorable;
  private final Character _match2;

  public SimpleContextSensitiveRule(final Character match, final Character predecessor, final Character successor, final String ignore, final String replacement) {
    super(match);
    _match2 = match;
    _predecessor = predecessor;
    _successor = successor;
    _replacement = Utils.toChars(replacement);
    _ignorable = Utils.toChars(ignore);
  }

  @Override public final List<Character> replacement() { return _replacement; }
  @Override protected final Character predecessor() { return _predecessor; }
  @Override protected final Character successor() { return _successor; }
  @Override protected final Character popper() { return ']'; }
  @Override protected final Character pusher() { return '['; }
  @Override protected final List<Character> ignorable() { return _ignorable; }

  @Override
  public String toString() {
    return String.format("%s < %s > %s -> %s", _predecessor, _match2, _successor, Utils.toString(_replacement));
  }

}
