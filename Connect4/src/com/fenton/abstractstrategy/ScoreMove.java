package com.fenton.abstractstrategy;

public class ScoreMove<M> {
  private final int _score;
  private final M _move;

  public ScoreMove(final int score, final M move) {
    _score = score;
    _move = move;
  }
  public M getMove() { return _move; }
  public int getScore() { return _score; }
}