package joephysics62.co.uk.old.lsystems.rules;

import java.util.List;
import java.util.stream.IntStream;

import joephysics62.co.uk.old.lsystems.turtle.IModule;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class StochasticContextFreeRule extends ContextFreeRule {

  private final IntegerDistribution _selector;
  private final List<List<IModule>> _replacements;

  public StochasticContextFreeRule(final Character match, final double[] weights, final List<List<IModule>> replacements) {
    super(match);
    if (weights.length != replacements.size()) {
      throw new IllegalArgumentException();
    }
    _replacements = replacements;
    _selector = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public List<IModule> replacement(final double... x) {
    return _replacements.get(_selector.sample());
  }

}
