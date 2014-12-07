package joephysics62.co.uk.lsystems;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PythagoreanTree implements LSystem {
	
	private static final Map<Character, List<Character>> MAP = new LinkedHashMap<>();
	static {
		MAP.put('0', Arrays.asList('1', '[', '0', ']', '0'));
		MAP.put('1', Arrays.asList('1', '1'));
	}
	
	@Override
	public List<Character> axiom() {
		return Arrays.asList('0');
	}

	@Override
	public List<Character> variables() {
		return Arrays.asList('0', '1');
	}

	@Override
	public List<Character> applyRule(Character input) {
		if (MAP.containsKey(input)) {
			return MAP.get(input);
		}
		return Arrays.asList(input);
	}

}
