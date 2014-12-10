package joephysics62.co.uk.lsystems;

import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;


public class StochasticRewriteSystem implements RewriteSystem {

  private final char _from;
  private final Rewrite[] _rewrites;
  private final EnumeratedIntegerDistribution _eid;

  public StochasticRewriteSystem(final char from, final double[] weights, final String[] rewrites) {
    _from = from;
    _rewrites = new Rewrite[weights.length];
    for (int i = 0; i < weights.length; i++) {
      _rewrites[i] = Rewrite.of(from, rewrites[i]);
    }
    _eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public boolean hasKey(final char key) {
    return key == _from;
  }

  @Override
  public Rewrite ruleForKey(final char key) {
    return _rewrites[_eid.sample()];
  }

}
