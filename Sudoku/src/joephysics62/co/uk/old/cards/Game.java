package joephysics62.co.uk.old.cards;

import java.util.List;

public class Game {
	private static final int INITIAL_CARDS = 2;
	private final List<Player> _players;
	private final Deck _deck = new Deck();
	
	public Game(List<Player> players) {
		_players = players;
		_deck.shuffle();
	}
	
	public void initialDeal() {
		for (int i = 0; i < INITIAL_CARDS; i++) {
			for (Player player : _players) {
				_deck.deal(player);
			}
		}
	}
}
