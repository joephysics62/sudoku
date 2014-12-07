package joephysics62.co.uk.lsystems;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Algae implements LSystem {
	
	private static final Map<Character, List<Character>> MAP = new LinkedHashMap<>();
	static {
		MAP.put('A', Arrays.asList('A', 'B'));
		MAP.put('B', Arrays.asList('A'));
	}
	
	@Override
	public List<Character> axiom() {
		return Arrays.asList('A');
	}
	
	@Override
	public List<Character> variables() {
		return Arrays.asList('A', 'B');
	}
	
	@Override
	public List<Character> applyRule(Character input) {
		if (MAP.containsKey(input)) {
			return MAP.get(input);
		}
		return Arrays.asList(input);
	}

}
