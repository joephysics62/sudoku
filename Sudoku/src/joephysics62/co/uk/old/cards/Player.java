package joephysics62.co.uk.old.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Player {
	
	private final String _name;
	private final List<Card> _hand = new ArrayList<>();
	
	public Player(final String name) {
		_name = name;
	}
	
	public Stream<Card> cards() {
		return _hand.stream();
	}
	
	public String getName() {
		return _name;
	}
	
	public void addToHand(Card card) {
		_hand.add(card);
	}
	
	public Set<Integer> handSums() {
		if (_hand.isEmpty()) {
			return Collections.emptySet();
		}
	    return recursiveSum(_hand.iterator(), Collections.singleton(0));
	}

	private Set<Integer> recursiveSum(Iterator<Card> iterator, Set<Integer> currentSums) {
		if (!iterator.hasNext()) {
			return currentSums;
		}
		final Card card = iterator.next();
		final Set<Integer> nextSums = new LinkedHashSet<>();
		for (Integer cardValue : card.values()) {
			for (Integer currentSum : currentSums) {
				nextSums.add(currentSum + cardValue);
			}
		}
		return recursiveSum(iterator, nextSums);
	}
}
