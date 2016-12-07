package com.fenton.abstractstrategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class MiniMax {

  private static final Map<Long, Integer> VALUES = new LinkedHashMap<>();

  private static <M> ScoreMove<M> miniMax(final AbstractStategyGame<M> game, final Player maximisingPlayer, final Player player, final int lookAheadCount) {
    M bestMove = null;

    final boolean isMax = maximisingPlayer == player;
    int bestVal = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    for (final M move : game.validMoves()) {
      final AbstractStategyGame<M> clonedGame = game.clone();
      clonedGame.makeMove(move, player);

      final long hash = clonedGame.hash();

      final int eval;
      if (VALUES.containsKey(hash)) {
        eval = VALUES.get(hash);
      }
      else {
        eval = calculateValue(maximisingPlayer, player, lookAheadCount, isMax, move, clonedGame);
        VALUES.put(hash, eval);
      }

      if (isMax) {
        if (eval > bestVal) {
          bestVal = eval;
          bestMove = move;
        }
      }
      else {
        if (eval < bestVal) {
          bestVal = eval;
          bestMove = move;
        }
      }
    }
    return new ScoreMove<>(bestVal, bestMove);
  }

  private static <M> int calculateValue(final Player maximisingPlayer,
      final Player player, final int lookAheadCount, final boolean isMax,
      final M move, final AbstractStategyGame<M> clonedGame) {
    final int eval;
    final boolean winningMove = clonedGame.isWinningMove(move, player);
    if (winningMove) {
      eval = isMax ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    }
    else if (lookAheadCount <= 1) {
      eval = clonedGame.boardVal(maximisingPlayer);
    }
    else {
      eval = miniMax(clonedGame, maximisingPlayer, player.nextPlayer(), lookAheadCount - 1).getScore();
    }
    return eval;
  }

  public static <M> M findBestMove(final AbstractStategyGame<M> game, final Player player, final int lookAheadCount) {
    final M move = miniMax(game, player, player, lookAheadCount).getMove();
    System.out.println("CACHE SIZE = " + VALUES.size());
    return move;
  }


}
