package com.fenton.abstractstrategy;

import java.io.IOException;
import java.util.Random;

public class GameController {

  private static final Random R = new Random();

  public <M> void startGame(final AbstractStategyGame<M> game, final int difficulty) throws IOException, InterruptedException {
    Player curr = Player.values()[R.nextInt(Player.values().length)];
    while (true) {
      game.print(System.out);
      System.out.println("Player " + curr + " (" + curr.getIcon() + ") turn:");
      final M column;
      if (curr.isHuman()) {
        column = game.waitForUserMove();
      }
      else {
        Thread.sleep(500);
        column = MiniMax.findBestMove(game, curr, difficulty);
      }
      if (game.isValidMove(column)) {
        if (game.makeMove(column, curr)) {
          System.out.println("Player " + curr + " wins!");
          game.print(System.out);
          return;
        }
        else if (game.hasMovesRemaining()) {
          curr = curr.nextPlayer();
        }
        else {
          System.out.println("A DRAW!");
          game.print(System.out);
          return;
        }
      }
    }
  }
}
