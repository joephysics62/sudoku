package com.fenton.connect4;

import java.io.IOException;

import com.fenton.abstractstrategy.MiniMax;
import com.fenton.abstractstrategy.Player;

public class Connect4Main {

  public static void main(final String[] args) throws IOException, InterruptedException {
    final Connect4 connect4 = new Connect4(7, 6);

    Player curr = Player.RED;
    while (true) {
      connect4.print(System.out);
      System.out.println("Player " + curr + " (" + curr.getIcon() + ") turn:");
      final int column;
      if (curr.isHuman()) {
        column = waitForInt() - 1;
      }
      else {
        Thread.sleep(500);
        column = MiniMax.findBestMove(connect4, curr, 6);
      }
      if (connect4.isValidMove(column)) {
        connect4.makeMove(column, curr);
        if (connect4.isWinningMove(column, curr)) {
          System.out.println("Player " + curr + " wins!");
          connect4.print(System.out);
          return;
        }
        else if (connect4.hasMovesRemaining()) {
          curr = Player.RED == curr ? Player.YELLOW : Player.RED;
        }
        else {
          System.out.println("A DRAW!");
          connect4.print(System.out);
          return;
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
