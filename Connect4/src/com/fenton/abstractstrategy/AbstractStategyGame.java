package com.fenton.abstractstrategy;

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

  int winVal(int lookahead);

  int startVal();

  void print(final PrintStream pstream);

}
