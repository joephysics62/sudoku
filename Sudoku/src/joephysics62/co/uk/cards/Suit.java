package joephysics62.co.uk.cards;

public enum Suit {
  CLUBS("Clubs", "\u2663"),
  SPADES("Spades", "\u2660"),
  DIAMONDS("Diamonds", "\u2666"),
  HEARTS("Hearts", "\u2665");
  
  private String _longName;
  private String _shortName;

  private Suit(final String longName, final String shortName) {
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
