package joephysics62.co.uk.cards;

import java.util.Arrays;
import java.util.List;

public class Pontoon {
	public static void main(String[] args) {
		List<Player> players = Arrays.asList(new Player("Bob"), new Player("Bill"));
		Game game = new Game(players);
		game.initialDeal();
		for (Player player : players) {
			System.out.println(player.getName());
			player.cards().forEach(System.out::println);
			System.out.println(player.handSums());
			System.out.println();
		}
	}
}
