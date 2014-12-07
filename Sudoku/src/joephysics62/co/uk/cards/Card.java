package joephysics62.co.uk.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Card {

	private final Rank _rank;
	private final Suit _suit;
	private Set<Integer> _values;

	public Card(Rank rank, Suit suit) {
		_rank = rank;
		_suit = suit;
		if (rank == Rank.ACE) {
			_values = Collections.unmodifiableSet(new LinkedHashSet<Integer>(Arrays.asList(1, 11)));
		}
		else {
			_values = Collections.singleton(Math.min(10, rank.ordinal() + 1));
		}
	}

	@Override
	public String toString() {
		return longName();
	}
	
	public String longName() {
		return _rank.longName() + " of " + _suit.longName();
	}
	
	public String shortName() {
		return _rank.shortName() + _suit.shortName();
	}

	public Set<Integer> values() {
		return _values;
	}

	public Rank getRank() {
		return _rank;
	}

	public Suit getSuit() {
		return _suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_rank == null) ? 0 : _rank.hashCode());
		result = prime * result + ((_suit == null) ? 0 : _suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Card other = (Card) obj;
		return Objects.equals(_rank, other._rank)
				&& Objects.equals(_suit, other._suit);
	}

}
