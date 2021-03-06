package com.fenton.abstractstrategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiniMax {

  private static final Map<Long, Integer> VALUES = new LinkedHashMap<>();
  private static final int WIN_VAL = (int) Math.pow(10, 8);

  private static final Random R = new Random();

  private static <M> ScoreMove<M> miniMax(final AbstractStategyGame<M> game, final Player maximisingPlayer, final Player player, final int lookAheadCount) {
    final List<M> validMoves = game.validMoves();
    if (validMoves.isEmpty()) {
      return new ScoreMove<>(0, null);
    }
    final boolean isMax = maximisingPlayer == player;
    int bestVal = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    final List<M> bestMoves = new ArrayList<>();
    for (final M move : validMoves) {
      final AbstractStategyGame<M> clonedGame = game.clone();
      final boolean isWinningMove = clonedGame.makeMove(move, player);

      final long hash = clonedGame.hash();

      final int eval;
      if (VALUES.containsKey(hash)) {
        eval = VALUES.get(hash);
      }
      else if (isWinningMove) {
        eval = (WIN_VAL + lookAheadCount) * (isMax ? 1 : - 1);
        VALUES.put(hash, eval);
      }
      else {
        eval = calculateValue(maximisingPlayer, player, lookAheadCount, clonedGame);
        VALUES.put(hash, eval);
      }

      if (isMax && eval > bestVal || !isMax && eval < bestVal) {
        bestVal = eval;
        bestMoves.clear();
        bestMoves.add(move);
      }
      else if (eval == bestVal) {
        bestMoves.add(move);
      }
    }

    final M bestMove = bestMoves.get(bestMoves.size() == 1 ? 0 : R.nextInt(bestMoves.size()));
    return new ScoreMove<>(bestVal, bestMove);
  }

  private static <M> int calculateValue(final Player maximisingPlayer, final Player player, final int lookAheadCount, final AbstractStategyGame<M> clonedGame) {
    if (lookAheadCount <= 1) {
      return clonedGame.boardVal(maximisingPlayer);
    }
    else {
      return miniMax(clonedGame, maximisingPlayer, player.nextPlayer(), lookAheadCount - 1).getScore();
    }
  }

  public static <M> M findBestMove(final AbstractStategyGame<M> game, final Player player, final int lookAheadCount) {
    return miniMax(game, player, player, lookAheadCount).getMove();
  }


}
