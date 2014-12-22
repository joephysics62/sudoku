package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.lsystems.rules.SimpleContextSensitiveRule;
import joephysics62.co.uk.lsystems.rules.StochasticContextFreeRule;

public abstract class UnparametricLSystem extends AbstractLSystem {

  private final Map<Character, TurtleElement> _map;

  public UnparametricLSystem() {
    final List<TurtleElement> elements = Arrays.asList(
      new TurtleElement('[', 0),
      new TurtleElement(']', 0),
      new TurtleElement('F', drawStep()),
      new TurtleElement('G', drawStep()),
      new TurtleElement('f', drawStep()),
      new TurtleElement('!', narrowing()),
      new TurtleElement('+', angle()),
      new TurtleElement('-', angle()),
      new TurtleElement('^', angle()),
      new TurtleElement('&', angle()),
      new TurtleElement('/', angle()),
      new TurtleElement('\\', angle()),
      new TurtleElement('|', 0)
    );
    _map = new LinkedHashMap<>();
    for (final TurtleElement turtleElement : elements) {
      _map.put(turtleElement.getId(), turtleElement);
    }
  }

  protected SimpleContextFreeRule contextFreeRule(final Character match, final String replacement) {
    return new SimpleContextFreeRule(match, toElemList(replacement));
  }

  protected Rule stochasticContextFreeRule(final char match, final double[] weights, final String[] replacementsArray) {
    final List<List<TurtleElement>> replacements = new ArrayList<>();
    for (final String string : replacementsArray) {
      replacements.add(toElemList(string));
    }
    return new StochasticContextFreeRule(match, weights, replacements);
  }

  protected Rule simpleContextSensitiveRule(final Character predecessor, final Character match, final Character successor,
      final String ignores, final String replacement) {
        return new SimpleContextSensitiveRule(predecessor, match, successor, ignores, toElemList(replacement));
  }

  private List<TurtleElement> toElemList(final String string) {
    return string.chars().mapToObj(c -> (char) c).map(c -> elementByChar(c)).collect(Collectors.toList());
  }

  private TurtleElement elementByChar(final Character c) {
    return _map.containsKey(c) ? _map.get(c) : new TurtleElement(c, 0);
  }

  protected abstract String axiomString();

  @Override
  public final List<TurtleElement> axiom() {
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
