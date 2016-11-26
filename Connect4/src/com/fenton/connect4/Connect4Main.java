package com.fenton.connect4;

import java.io.IOException;

public class Connect4Main {

  public static void main(final String[] args) throws IOException, InterruptedException {
    final Board connect4Board = new Board(7, 6);

    Player curr = Player.RED;
    boolean gameInPlay = true;
    while (gameInPlay) {
      connect4Board.printBoard(System.out);
      final int column = waitForInt() - 1;
      if (connect4Board.addToColumn(curr, column)) {
        if (connect4Board.hasPlayedWinningMove(curr, column)) {
          System.out.println("Player " + curr + " wins!");
          connect4Board.printBoard(System.out);
          gameInPlay = false;
        }
        else {
          curr = Player.RED == curr ? Player.YELLOW : Player.RED;
        }
      }
    }
  }

  private static int waitForInt() throws IOException {
    while (true) {
      final int readByte = System.in.read();
      final char readChar = (char) readByte;
      final String readString = String.valueOf(readChar);
      if (readString.matches("[1-7]")) {
        return Integer.valueOf(readString);
      }
    }
  }

}
