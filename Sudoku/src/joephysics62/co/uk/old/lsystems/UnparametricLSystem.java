package joephysics62.co.uk.old.lsystems;

import static joephysics62.co.uk.old.lsystems.turtle.Module.draw;
import static joephysics62.co.uk.old.lsystems.turtle.Module.drawf;
import static joephysics62.co.uk.old.lsystems.turtle.Module.identity;
import static joephysics62.co.uk.old.lsystems.turtle.Module.incrementColour;
import static joephysics62.co.uk.old.lsystems.turtle.Module.left;
import static joephysics62.co.uk.old.lsystems.turtle.Module.move;
import static joephysics62.co.uk.old.lsystems.turtle.Module.narrow;
import static joephysics62.co.uk.old.lsystems.turtle.Module.pitchDown;
import static joephysics62.co.uk.old.lsystems.turtle.Module.pitchUp;
import static joephysics62.co.uk.old.lsystems.turtle.Module.pop;
import static joephysics62.co.uk.old.lsystems.turtle.Module.push;
import static joephysics62.co.uk.old.lsystems.turtle.Module.right;
import static joephysics62.co.uk.old.lsystems.turtle.Module.rollLeft;
import static joephysics62.co.uk.old.lsystems.turtle.Module.rollRight;
import static joephysics62.co.uk.old.lsystems.turtle.Module.uturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.old.lsystems.rules.Rule;
import joephysics62.co.uk.old.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.old.lsystems.rules.SimpleContextSensitiveRule;
import joephysics62.co.uk.old.lsystems.rules.StochasticContextFreeRule;
import joephysics62.co.uk.old.lsystems.turtle.IModule;

public abstract class UnparametricLSystem extends AbstractLSystem {

  private final Map<Character, IModule> _map;

  public UnparametricLSystem() {
    final List<IModule> elements = Arrays.asList(
      push(),
      pop(),
      drawf(drawStep()),
      draw('G', drawStep()),
      move('f', drawStep()),
      narrow(narrowing()),
      left(angle()),
      right(angle()),
      pitchUp(angle()),
      pitchDown(angle()),
      rollLeft(angle()),
      rollRight(angle()),
      uturn(),
      incrementColour()
    );
    _map = new LinkedHashMap<>();
    for (final IModule turtleElement : elements) {
      _map.put(turtleElement.getId(), turtleElement);
    }
  }

  protected SimpleContextFreeRule contextFreeRule(final Character match, final String replacement) {
    return new SimpleContextFreeRule(match, toElemList(replacement));
  }

  protected Rule stochasticContextFreeRule(final char match, final double[] weights, final String[] replacementsArray) {
    final List<List<IModule>> replacements = new ArrayList<>();
    for (final String string : replacementsArray) {
      replacements.add(toElemList(string));
    }
    return new StochasticContextFreeRule(match, weights, replacements);
  }

  protected Rule simpleContextSensitiveRule(final Character predecessor, final Character match, final Character successor,
      final String ignores, final String replacement) {
        return new SimpleContextSensitiveRule(predecessor, match, successor, ignores, toElemList(replacement));
  }

  private List<IModule> toElemList(final String string) {
    return string.chars().mapToObj(c -> (char) c).map(c -> elementByChar(c)).collect(Collectors.toList());
  }

  private IModule elementByChar(final Character c) {
    return _map.containsKey(c) ? _map.get(c) : identity(c);
  }

  protected abstract String axiomString();

  @Override
  public final List<IModule> axiom() {
    return toElemList(axiomString());
  }

  protected abstract double angle();
  protected double drawStep() {
    return 0.1;
  }
  protected double narrowing() {
    return 1.0;
  }

}
