package joephysics62.co.uk.lsystems;

public interface RewriteSystem {

  boolean hasKey(char key);

  Rewrite ruleForKey(char key);

}