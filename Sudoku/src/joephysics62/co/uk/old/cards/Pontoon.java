package joephysics62.co.uk.old.cards;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Pontoon {
	public static void main(String[] args) {
		List<Player> players = Arrays.asList(new Player("Bob"), new Player("Bill"));
		Game game = new Game(players);
		game.initialDeal();
		for (Player player : players) {
			Optional<Integer> score = getScore(player);
			String handAsString = player.cards().map(Card::shortName).collect(Collectors.joining(","));
			if (score.isPresent()) {
				System.out.println(String.format("%s: %s score = %s", player.getName(), handAsString, score.get()));
			}
			else {
				System.out.println(String.format("%s: %s is bust!", player.getName(), handAsString));
			}
		}
	}

	private static Optional<Integer> getScore(Player player) {
		return player.handSums().stream().filter(x -> x <= 21).max(Integer::compare);
	}
}
