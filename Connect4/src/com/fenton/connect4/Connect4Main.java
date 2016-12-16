package com.fenton.connect4;

import java.io.IOException;

import com.fenton.abstractstrategy.GameController;

public class Connect4Main {

  public static void main(final String[] args) throws IOException, InterruptedException {

    while (true) {
    final Connect4 connect4 = new Connect4(7, 6);

    final GameController gameController = new GameController();
    gameController.startGame(connect4, 8);
    }
  }

}
