package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.CharacterMapLSystem;
import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.RewriteSystem;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class BushExample3d extends CharacterMapLSystem {

  private static final TurtleMoves moves = new TurtleMoves(
      Turtle.draw('A'),
      Turtle.draw('F'),
      Turtle.draw('S'),
      Turtle.identity('L'),
      Turtle.identity('\''),
      Turtle.identity('!'),
      Turtle.push('['),
      Turtle.pop(']'),
      Turtle.left('-'),
      Turtle.right('+'),
      Turtle.up('^'),
      Turtle.down('&'),
      Turtle.rollRight('/')
  );
  private static final RewriteSystem rewriteRules = new DeterministicRewriteSystem(
      Rewrite.of('A', "[&FL!A]/////'[&FL!A]///////'[&FL!A]"),
      Rewrite.of('F', "S/////F"),
      Rewrite.of('S', "FL")
  );
  private static final String axiomString = "A";
  private static final Double angleDegrees = 5.0;

  public BushExample3d() {
    super(moves, rewriteRules, axiomString, angleDegrees);
  }

}
