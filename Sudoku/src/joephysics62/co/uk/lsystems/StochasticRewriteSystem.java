package joephysics62.co.uk.lsystems;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;


public class StochasticRewriteSystem implements RewriteSystem {

  private final char _from;
  private final Rewrite[] _rewrites;
  private final EnumeratedIntegerDistribution _eid;

  public StochasticRewriteSystem(final char from, final Map<String, Double> rules) {
    _from = from;
    final int size = rules.size();
    final double[] weights = new double[size];
    _rewrites = new Rewrite[size];

    int i = 0;
    for (final Entry<String, Double> entry : rules.entrySet()) {
      weights[i] = entry.getValue();
      _rewrites[i] = Rewrite.of(_from, entry.getKey());
      i++;
    }
    _eid = new EnumeratedIntegerDistribution(IntStream.range(0, size).toArray(), weights);
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
