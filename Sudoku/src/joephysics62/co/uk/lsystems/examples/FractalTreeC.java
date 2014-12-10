package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;


public class FractalTreeC extends CharacterMapLSystem {

  public FractalTreeC() {
    super(
        new TurtleMoves(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+'), Turtle.push('['), Turtle.pop(']')),
        new DeterministicRewriteSystem(Rewrite.of('F', "FF-[-F+F+F]+[+F-F-F]")),
        "F",
        22.5
    );
  }

}
