package com.fenton.abstractstrategy;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public interface AbstractStategyGame<M> {
  AbstractStategyGame<M> clone();

  boolean hasMovesRemaining();

  boolean isValidMove(M move);
  List<M> validMoves();

  void makeMove(M move, Player player);

  boolean isWinningMove(M move, Player player);

  int boardVal(Player player);

  void print(final PrintStream pstream);

  long hash();

  M waitForUserMove() throws IOException;

}
