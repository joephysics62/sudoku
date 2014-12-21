package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class StochasticContextFreeRule extends SingleCharacterRule {

  private final List<List<Character>> _replacements = new ArrayList<>();
  private final IntegerDistribution _selector;

  public StochasticContextFreeRule(final Character match, final double[] weights, final String[] replacements) {
    super(match);
    for (final String string : replacements) {
      _replacements.add(string.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    }
    _selector = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);
  }

  @Override
  public List<Character> replacement() {
    return _replacements.get(_selector.sample());
  }

}
