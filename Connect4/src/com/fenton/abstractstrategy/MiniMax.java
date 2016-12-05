package com.fenton.abstractstrategy;

public class MiniMax {

  private static <M> ScoreMove<M> miniMax(final AbstractStategyGame<M> game, final Player maximisingPlayer, final Player player, final int lookAheadCount) {
    M bestMove = null;

    final boolean isMax = maximisingPlayer == player;
    int bestVal = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    for (final M move : game.validMoves()) {
      final AbstractStategyGame<M> clonedGame = game.clone();
      clonedGame.makeMove(move, player);
      final boolean winningMove = clonedGame.isWinningMove(move, player);
      final int eval;
      if (winningMove) {
        eval = isMax ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      }
      else if (lookAheadCount <= 1) {
        eval = clonedGame.boardVal(maximisingPlayer);
      }
      else {
        eval = miniMax(clonedGame, maximisingPlayer, player.nextPlayer(), lookAheadCount - 1).getScore();
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

  public static <M> M findBestMove(final AbstractStategyGame<M> game, final Player player, final int lookAheadCount) {
    return miniMax(game, player, player, lookAheadCount).getMove();
  }


}
