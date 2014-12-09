package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class SierpinskiGasket extends CharacterMapLSystem {

  public SierpinskiGasket() {
    super(
        Arrays.asList(Turtle.draw('f'), Turtle.draw('g'), Turtle.left('-'), Turtle.right('+')),
        Arrays.asList(RewriteRule.of('g', "f+g+f"), RewriteRule.of('f', "g-f-g")),
        "f"
    );
  }

}
