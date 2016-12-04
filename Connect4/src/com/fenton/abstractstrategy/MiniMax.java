package com.fenton.abstractstrategy;

import java.util.Objects;

public class MiniMax {

  private static <M> ScoreMove<M> miniMax(final AbstractStategyGame<M> game, final Player maximisingPlayer, final Player player, final int lookAheadCount) {
    M bestMove = null;

    final int parity = Objects.equals(maximisingPlayer, player) ? 1 : -1;
    int bestVal = -parity * game.startVal();
    for (final M move : game.validMoves()) {
      final AbstractStategyGame<M> clonedGame = game.clone();
      clonedGame.makeMove(move, player);
      final boolean winningMove = clonedGame.isWinningMove(move, player);
      final int eval;
      if (winningMove) {
        eval = parity * clonedGame.winVal(lookAheadCount);
      }
      else if (lookAheadCount <= 1) {
        eval = parity * clonedGame.boardVal(player);
      }
      else {
        eval = miniMax(clonedGame, maximisingPlayer, player.nextPlayer(), lookAheadCount - 1).getScore();
      }

      if (Objects.equals(maximisingPlayer, player)) {
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

  public static <M> M findBestMove(final AbstractStategyGame<M> game, final Player player, final int lookAheadCount) {
    return miniMax(game, player, player, lookAheadCount).getMove();
  }


}
