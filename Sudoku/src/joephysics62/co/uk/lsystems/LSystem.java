package joephysics62.co.uk.lsystems;

import java.util.List;

public interface LSystem {

	public abstract List<Character> axiom();

	public abstract List<Character> variables();

	public abstract List<Character> applyRule(Character input);

}