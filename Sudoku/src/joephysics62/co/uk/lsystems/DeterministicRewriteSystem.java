package joephysics62.co.uk.lsystems;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class DeterministicRewriteSystem implements Iterable<Rewrite>, RewriteSystem {

  private final Map<Character, Rewrite> _rewriteMap = new LinkedHashMap<>();

  public DeterministicRewriteSystem(final Rewrite... rules) {
    for (final Rewrite rewriteRule : rules) {
      _rewriteMap.put(rewriteRule.getFrom(), rewriteRule);
    }
  }

  @Override
  public boolean hasKey(final char key) {
    return _rewriteMap.containsKey(key);
  }

  @Override
  public Rewrite ruleForKey(final char key) {
    return _rewriteMap.get(key);
  }

  @Override
  public Iterator<Rewrite> iterator() {
    return _rewriteMap.values().iterator();
  }

}
