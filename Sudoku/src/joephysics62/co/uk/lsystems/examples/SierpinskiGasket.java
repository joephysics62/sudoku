package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class SierpinskiGasket extends CharacterMapLSystem {

  public SierpinskiGasket() {
    super(
        new TurtleMoves(Turtle.draw('f'), Turtle.draw('g'), Turtle.left('-'), Turtle.right('+')),
        new DeterministicRewriteSystem(Rewrite.of('g', "f+g+f"), Rewrite.of('f', "g-f-g")),
        "f",
        60.0
    );
  }

}
