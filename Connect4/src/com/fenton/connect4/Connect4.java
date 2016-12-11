package com.fenton.connect4;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

  private final long[][] _zobristRed;
  private final long[][] _zobristYellow;

  private long _hash;

  public Connect4(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = new Player[height][width];
    _currHeights = new int[width];
    final Random r = new Random(123l);
    _hash = r.nextLong();
    _zobristRed = initZobrist(width, height, r);
    _zobristYellow = initZobrist(width, height, r);
  }

  private long[][] initZobrist(final int width, final int height, final Random random) {
    final long[][] zobrist = new long[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        zobrist[r][c] = random.nextLong();
      }
    }
    return zobrist;
  }

  @Override
  public long hash() {
    return _hash;
  }

  private Connect4(final int width, final int height, final Player[][] pieces, final int[] currHeights,
                   final int movesCount, final long[][] zobristRed, final long[][] zobristYellow, final long hash) {
    _width = width;
    _height = height;
    _pieces = pieces;
    _currHeights = currHeights;
    _movesCount = movesCount;
    _zobristRed = zobristRed;
    _zobristYellow = zobristYellow;
    _hash = hash;
  }

  @Override
  public AbstractStategyGame<Integer> clone() {
    final Player[][] pieces = new Player[_height][_width];
    for (int row = 0; row < _height; row++) {
      pieces[row] = _pieces[row].clone();
    }
    return new Connect4(_width, _height, pieces, _currHeights.clone(), _movesCount, _zobristRed, _zobristYellow, _hash);
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
    if (player == Player.RED) {
      _hash ^= _zobristRed[heightAtCol][move];
    }
    else if (player == Player.YELLOW) {
      _hash ^= _zobristYellow[heightAtCol][move];
    }
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

  @Override
  public Integer waitForUserMove() throws IOException {
    while (true) {
      final int readByte = System.in.read();
      final char readChar = (char) readByte;
      final String readString = String.valueOf(readChar);
      if (readString.matches("[1-" + _width + "]")) {
        return Integer.valueOf(readString) - 1;
      }
    }
  }

}
