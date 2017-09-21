package com.fenton.connect4;

public enum GridDirection {
  VERTICAL(1, 0),
  HORIZONTAL(0, 1),
  DIAGONAL_ASC(1, 1),
  DIAGONAL_DESC(1, -1);

  private final int _rowStep;
  private final int _colStep;

  private GridDirection(final int rowStep, final int colStep) {
    _rowStep = rowStep;
    _colStep = colStep;
  }

  public int rowStep() {
    return _rowStep;
  }
  public int colStep() {
    return _colStep;
  }

}
