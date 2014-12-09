package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.Collections;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class GeneralKochCurve extends CharacterMapLSystem {

  public GeneralKochCurve(final String drawRewrite) {
    super(
        Arrays.asList(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+')),
        Collections.singletonList(RewriteRule.of('F', drawRewrite)),
        "F-F-F-F"
     );
  }

}
