package joephysics62.co.uk.old.lsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.old.lsystems.rules.Rule;
import joephysics62.co.uk.old.lsystems.turtle.IModule;

public abstract class AbstractLSystem implements LSystem {

  @Override
  public final List<IModule> generate(final int iterations) {
    return recursiveReplace(axiom(), 0, iterations);
  }

  private List<IModule> recursiveReplace(final List<IModule> chars, final int iterations, final int limit) {
    if (iterations >= limit || chars.size() > MAX_ELEMENTS) {
      return chars;
    }
    final List<IModule> newChars = new ArrayList<>();
    for (int index = 0; index < chars.size(); index++) {
      newChars.addAll(findAndApply(index, chars));
    }
    return recursiveReplace(newChars, iterations + 1, limit);
  }

  private List<IModule> findAndApply(final int index, final List<IModule> chars) {
    final List<Rule> matches = new ArrayList<>();
    for (final Rule rule : rules()) {
      if (rule.matches(index, chars)) {
        matches.add(rule);
      }
    }
    if (matches.size() == 1) {
      return matches.get(0).replacement(chars.get(index).getParameters());
    }
    else if (matches.size() > 1) {
      return matches.get(matches.size() - 1).replacement(chars.get(index).getParameters());
    }
    else {
      return Collections.singletonList(chars.get(index));
    }
  }

}
