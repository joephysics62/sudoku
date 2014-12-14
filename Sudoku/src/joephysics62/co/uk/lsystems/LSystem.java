package joephysics62.co.uk.lsystems;


public interface LSystem {

	String axiom();

	String applyRule(Character input);
}