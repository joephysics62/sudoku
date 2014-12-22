package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ExampleParametricRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class ExampleParametricLSystem extends AbstractLSystem {

  @Override
  public List<TurtleElement> axiom() {
    return Arrays.asList(new TurtleElement('F', 1.0));
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(new ExampleParametricRule('F'));
  }

}
