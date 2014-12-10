package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class GeneralKochCurve extends CharacterMapLSystem {

  public GeneralKochCurve(final String drawRewrite) {
    super(
        new TurtleMoves(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+')),
        new DeterministicRewriteSystem(Rewrite.of('F', drawRewrite)),
        "F-F-F-F",
        90.0
     );
  }

}
