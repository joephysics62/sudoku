package joephysics62.co.uk.cards;

public enum Rank {
	ACE("Ace", "A"),
	TWO("Two", "2"),
	THREE("Three", "3"),
	FOUR("Four", "4"),
	FIVE("Five", "5"),
	SIX("Six", "6"),
	SEVEN("Seven", "7"),
	EIGHT("Eight", "8"),
	NINE("Nine", "9"),
	TEN("Ten", "10"),
	JACK("Jack", "J"),
	QUEEN("Queen", "Q"),
	KING("King", "K");
	
	private String _longName;
	private String _shortName;

	private Rank(final String longName, final String shortName) {
		_longName = longName;
		_shortName = shortName;
	}
	
	public String longName() {
		return _longName;
	}
	
	public String shortName() {
		return _shortName;
	}
}
