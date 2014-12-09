package joephysics62.co.uk.lsystems;

import java.util.List;

import joephysics62.co.uk.lsystems.turtle.Turtle;

public interface LSystem {

	List<Turtle> axiom();

	List<Turtle> applyRule(Turtle input);

}