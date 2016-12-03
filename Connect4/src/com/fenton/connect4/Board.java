package com.fenton.connect4;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

  private static final int LINE_SIZE_TO_WIN = 4;
  private final int _width;
  private final int _height;
  private final Player[][] _pieces;
  private final int[] _currHeights;
  private int _movesCount;

  public Board(final int width, final int height) {
    _width = width;
    _height = height;
    _pieces = new Player[height][width];
    _currHeights = new int[width];
  }

  private Board(final int width, final int height, final Player[][] pieces, final int[] currHeights, final int movesCount) {
    _width = width;
    _height = height;
    _pieces = pieces;
    _currHeights = currHeights;
    _movesCount = movesCount;
  }

  private Board cloneBoard() {
    final Player[][] pieces = new Player[_height][_width];
    for (int row = 0; row < _height; row++) {
      pieces[row] = _pieces[row].clone();
    }
    return new Board(_width, _height, pieces, _currHeights.clone(), _movesCount);
  }

  public boolean hasMovesRemaining() {
    return _movesCount < _width * _height;
  }

  public boolean isValidMove(final int column) {
    return column >= 0 && column < _width && _currHeights[column] < _height;
  }

  public void makeMove(final Player player, final int column) {
    final int heightAtCol = _currHeights[column];
    _pieces[heightAtCol][column] = player;
    _currHeights[column]++;
    _movesCount++;
  }

  public void printBoard(final PrintStream pstream) {
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

  public boolean isWinningMove(final Player curr, final int newPieceCol) {
    final int newPieceRow = _currHeights[newPieceCol] - 1;

    return isVerticalWin(curr, newPieceRow, newPieceCol)
        || isHorizontalWin(curr, newPieceRow, newPieceCol)
        || isDiagonalAscWin(curr, newPieceRow, newPieceCol)
        || isDiagonalDescWin(curr, newPieceRow, newPieceCol);
  }

  private int lineSizeInDirection(final int initialLineSize, final int newPieceRow, final int newPieceCol,
                                  final Player curr, final IntFunction<Integer> rowFunc, final IntFunction<Integer> colFunc) {
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

  private boolean isPlayersPiece(final Player player, final int row, final int col) {
    if (row < 0 || col < 0 || row >= _height || col >= _width) {
      return false;
    }
    return _pieces[row][col] == player;
  }

  public static class ScoreMove {
    private final int _score;
    private final Integer _move;

    private ScoreMove(final int score, final Integer move) {
      _score = score;
      _move = move;
    }
    public Integer getMove() { return _move; }
    public int getScore() { return _score; }
  }

  public ScoreMove minMax(final Board board, final Player maximisingPlayer, final Player player, final int lookAheadCount) {
    Integer bestMove = null;
    int bestVal = ((maximisingPlayer == player) ? -1 : 1) * (int) Math.pow(10, LINE_SIZE_TO_WIN + 1);
    for (int col = 0; col < _width; col++) {
      if (board.isValidMove(col)) {
        final Board clonedBoard = board.cloneBoard();
        clonedBoard.makeMove(player, col);
        final boolean winningMove = clonedBoard.isWinningMove(player, col);
        final int eval;
        if (winningMove) {
          eval = ((maximisingPlayer == player) ? 1 : -1) * (int) Math.pow(10, LINE_SIZE_TO_WIN);
        }
        else if (lookAheadCount <= 1) {
          eval = ((maximisingPlayer == player) ? 1 : -1) * clonedBoard.boardStrengthForPlayer(player);
        }
        else {
          eval = minMax(clonedBoard, maximisingPlayer, player.nextPlayer(), lookAheadCount - 1).getScore();
        }

        if (maximisingPlayer == player) {
          if (eval > bestVal) {
            bestVal = eval;
            bestMove = col;
          }
        }
        else {
          if (eval < bestVal) {
            bestVal = eval;
            bestMove = col;
          }
        }
      }
    }
    return new ScoreMove(bestVal, bestMove);
  }


  private int boardStrengthForPlayer(final Player player) {
    // search horizontals
    int weighting = 0;

    weighting += calculateWeight(GridUtils.horizontalLineCount(_pieces, player));
    weighting += calculateWeight(GridUtils.verticalLineCount(_pieces, player));
    weighting += calculateWeight(GridUtils.diagonalAscLineCount(_pieces, player));
    weighting += calculateWeight(GridUtils.diagonalDescLineCount(_pieces, player));
    return weighting;
  }

  private int calculateWeight(final Map<Integer, Integer> horizontalLineCount) {
    int weighting = 0;
    for (final Entry<Integer, Integer> entry : horizontalLineCount.entrySet()) {
      final Integer lineSize = entry.getKey();
      final Integer occurs = entry.getValue();
      weighting += occurs * Math.pow(10, lineSize);
    }
    return weighting;
  }

}
