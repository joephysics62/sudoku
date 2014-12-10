package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.StochasticRewriteSystem;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class SimpleStochasticPlant extends CharacterMapLSystem {

  public SimpleStochasticPlant() {
    super(
        new TurtleMoves(Turtle.draw('F'), Turtle.push('['), Turtle.pop(']'), Turtle.left('-'), Turtle.right('+')),
        new StochasticRewriteSystem(
            'F',
            new double[] {1, 1, 1},
            new String[] {"F[+F]F[-F]F", "F[+F]F", "F[-F]F"}
        ),
        "F",
        35.0
    );
  }


}
