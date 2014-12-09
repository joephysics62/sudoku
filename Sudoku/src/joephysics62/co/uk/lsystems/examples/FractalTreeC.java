package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;


public class FractalTreeC extends CharacterMapLSystem {

  public FractalTreeC() {
    super(
        Arrays.asList(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+'), Turtle.push('['), Turtle.pop(']')),
        Arrays.asList(RewriteRule.of('F', "FF-[-F+F+F]+[+F-F-F]")),
        "F"
    );
  }

}
