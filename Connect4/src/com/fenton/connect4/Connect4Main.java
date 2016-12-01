package com.fenton.connect4;

import java.io.IOException;

public class Connect4Main {

  public static void main(final String[] args) throws IOException, InterruptedException {
    final Board connect4Board = new Board(7, 6);

    Player curr = Player.RED;
    while (true) {
      connect4Board.printBoard(System.out);
      System.out.println("Player " + curr + " (" + curr.getIcon() + ") turn:");
      final int column;
      if (curr.isHuman()) {
        column = waitForInt() - 1;
      }
      else {
        Thread.sleep(500);
        column = connect4Board.minMax(connect4Board, curr, curr, 6).getMove();
      }
      if (connect4Board.isValidMove(column)) {
        connect4Board.makeMove(curr, column);
        if (connect4Board.isWinningMove(curr, column)) {
          System.out.println("Player " + curr + " wins!");
          connect4Board.printBoard(System.out);
          return;
        }
        else if (connect4Board.hasMovesRemaining()) {
          curr = Player.RED == curr ? Player.YELLOW : Player.RED;
        }
        else {
          System.out.println("A DRAW!");
          connect4Board.printBoard(System.out);
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
