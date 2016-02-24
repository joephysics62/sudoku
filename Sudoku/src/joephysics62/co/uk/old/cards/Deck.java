package joephysics62.co.uk.old.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Deck {

	private final List<Card> _cards = new ArrayList<>();

	public Deck() {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				_cards.add(new Card(rank, suit));
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(_cards);
	}
	
	public Stream<Card> cards() {
		return _cards.stream();
	}
	
	public void deal(final Player player) {
		player.addToHand(_cards.remove(0));
	}

}
