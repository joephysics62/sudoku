package com.fenton.connect4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fenton.abstractstrategy.AbstractStategyGame;
import com.fenton.abstractstrategy.Player;

public class Connect4 implements AbstractStategyGame<Integer> {

  private static final int LINE_SIZE_TO_WIN = 4;
  private final int _width;
  private final int _height;
  private final Player[][] _pieces;
  private final int[] _currHeights;
  private int _movesCount;

  public Connect4(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = new Player[height][width];
    _currHeights = new int[width];
  }

  private Connect4(final int width, final int height, final Player[][] pieces, final int[] currHeights, final int movesCount) {
    _width = width;
    _height = height;
    _pieces = pieces;
    _currHeights = currHeights;
    _movesCount = movesCount;
  }

  @Override
  public AbstractStategyGame<Integer> clone() {
    final Player[][] pieces = new Player[_height][_width];
    for (int row = 0; row < _height; row++) {
      pieces[row] = _pieces[row].clone();
    }
    return new Connect4(_width, _height, pieces, _currHeights.clone(), _movesCount);
  }

  @Override
  public boolean hasMovesRemaining() {
    return _movesCount < _width * _height;
  }

  @Override
  public boolean isValidMove(final Integer move) {
    return move >= 0 && move < _width && _currHeights[move] < _height;
  }

  @Override
  public List<Integer> validMoves() {
    final List<Integer> moves = new ArrayList<>();
    for (int col = 0; col < _width; col++) {
      if (isValidMove(col)) {
        moves.add(col);
      }
    }
    return moves;
  }

  @Override
  public void makeMove(final Integer move, final Player player) {
    final int heightAtCol = _currHeights[move];
    _pieces[heightAtCol][move] = player;
    _currHeights[move]++;
    _movesCount++;
  }

  @Override
  public boolean isWinningMove(final Integer move, final Player player) {
    final int newPieceRow = _currHeights[move] - 1;

    return isVerticalWin(player, newPieceRow, move)
        || isHorizontalWin(player, newPieceRow, move)
        || isDiagonalAscWin(player, newPieceRow, move)
        || isDiagonalDescWin(player, newPieceRow, move);
  }

  private int lineSizeInDirection(final int initialLineSize, final int newPieceRow, final int newPieceCol, final Player curr, final IntFunction<Integer> rowFunc, final IntFunction<Integer> colFunc) {
    int lineSize = initialLineSize;
    int row = newPieceRow;
    int col = newPieceCol;
    while(true) {
      row = rowFunc.apply(row);
      col = colFunc.apply(col);
      if (isPlayersPiece(curr, row, col)) {
        lineSize++;
      }
      else {
        break;
      }
    }
    return lineSize;
  }

  private boolean isPlayersPiece(final Player player, final int row, final int col) {
    if (row < 0 || col < 0 || row >= _height || col >= _width) {
      return false;
    }
    return _pieces[row][col] == player;
  }


  private boolean isVerticalWin(final Player curr, final int newPieceRow, final int newPieceCol) {
    final int lineSize = lineSizeInDirection(1, newPieceRow, newPieceCol, curr, r -> r - 1, c -> c);
    return lineSize >= LINE_SIZE_TO_WIN;
  }

  private boolean isHorizontalWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r, c -> c - 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r, c -> c + 1) >= LINE_SIZE_TO_WIN;
  }

  private boolean isDiagonalAscWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r + 1, c -> c + 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r - 1, c -> c - 1) >= LINE_SIZE_TO_WIN;
  }

  private boolean isDiagonalDescWin(final Player curr, final int row, final int col) {
    final int lineSize = lineSizeInDirection(1, row, col, curr, r -> r - 1, c -> c + 1);
    if (lineSize >= LINE_SIZE_TO_WIN) {
      return true;
    }
    return lineSizeInDirection(lineSize, row, col, curr, r -> r + 1, c -> c - 1) >= LINE_SIZE_TO_WIN;
  }

  @Override
  public int boardVal(final Player player) {
    int weighting = 0;
    weighting += GridUtils.horizontalWeight(_pieces, player, LINE_SIZE_TO_WIN);
    weighting += GridUtils.verticalWeight(_pieces, player, LINE_SIZE_TO_WIN);
    weighting += GridUtils.diagonalAscWeight(_pieces, player, LINE_SIZE_TO_WIN);
    weighting += GridUtils.diagonalDescWeight(_pieces, player, LINE_SIZE_TO_WIN);
    return weighting;
  }

  @Override
  public void print(final PrintStream pstream) {
    IntStream
    .range(0, _height)
    .map(r -> _height - r - 1)
    .forEach(rowf -> {
      final Stream<String> rowVals = IntStream.range(0, _width)
                                        .mapToObj(c -> _pieces[rowf][c])
                                        .map(v -> v == null ? " " : v.getIcon());
      printRow(rowVals, pstream);
    });
    printRow(IntStream.rangeClosed(1, _width).mapToObj(Integer::valueOf), pstream);
    pstream.println();
  }

  private <T> void printRow(final Stream<T> rowObjs, final PrintStream pstream) {
    pstream.print("|");
    rowObjs.map(o -> o + "|").forEach(pstream::print);
    pstream.println();
  }

}
