package joephysics62.co.uk.lsystems.rules;

import java.util.List;
import java.util.stream.IntStream;

import joephysics62.co.uk.lsystems.TurtleElement;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class StochasticContextFreeRule extends ContextFreeRule {

  private final IntegerDistribution _selector;
  private final List<List<TurtleElement>> _replacements;

  public StochasticContextFreeRule(final Character match, final double[] weights, final List<List<TurtleElement>> replacements) {
    super(match);
    _replacements = replacements;
    _selector = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public List<TurtleElement> replacement(final double... x) {
    return _replacements.get(_selector.sample());
  }

}
