package com.example.joe.connect4app.game;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

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

    public void reset() {
        Arrays.fill(_currHeights, 0);
        _movesCount = 0;
        for (int row = 0; row < _height; row++) {
            Arrays.fill(_pieces[row], null);
        }
        _isClosed = false;
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

    private boolean _isClosed = false;

    public void setClosed() {
        _isClosed = true;
    }
    public boolean isClosed() {
        return _isClosed;
    }


    public boolean isWinningMove(final Player curr, final int newPieceCol) {
        final int newPieceRow = _currHeights[newPieceCol] - 1;

        return isVerticalWin(curr, newPieceRow, newPieceCol)
                || isHorizontalWin(curr, newPieceRow, newPieceCol)
                || isDiagonalAscWin(curr, newPieceRow, newPieceCol)
                || isDiagonalDescWin(curr, newPieceRow, newPieceCol);
    }

    public Player playerAt(int row, int col) {
        return _pieces[row][col];
    }

    private int lineSizeInDirection(final int initialLineSize, final int newPieceRow, final int newPieceCol,
                                    final Player curr, final IntFunc rowFunc, final IntFunc colFunc) {
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

    private static IntFunc IDENTITY = new IntFunc() {
        @Override
        public int apply(int input) {
            return input;
        }
    };

    private static IntFunc DECREMENT = new IntFunc() {
        @Override
        public int apply(int input) {
            return input - 1;
        }
    };

    private static IntFunc INCREMENT = new IntFunc() {
        @Override
        public int apply(int input) {
            return input + 1;
        }
    };

    private boolean isVerticalWin(final Player curr, final int newPieceRow, final int newPieceCol) {
        final int lineSize = lineSizeInDirection(1, newPieceRow, newPieceCol, curr, DECREMENT, IDENTITY);
        return lineSize >= LINE_SIZE_TO_WIN;
    }

    private boolean isHorizontalWin(final Player curr, final int row, final int col) {
        final int lineSize = lineSizeInDirection(1, row, col, curr, IDENTITY, DECREMENT);
        if (lineSize >= LINE_SIZE_TO_WIN) {
            return true;
        }
        return lineSizeInDirection(lineSize, row, col, curr, IDENTITY, INCREMENT) >= LINE_SIZE_TO_WIN;
    }

    private boolean isDiagonalAscWin(final Player curr, final int row, final int col) {
        final int lineSize = lineSizeInDirection(1, row, col, curr, INCREMENT, INCREMENT);
        if (lineSize >= LINE_SIZE_TO_WIN) {
            return true;
        }
        return lineSizeInDirection(lineSize, row, col, curr, DECREMENT, DECREMENT) >= LINE_SIZE_TO_WIN;
    }

    private boolean isDiagonalDescWin(final Player curr, final int row, final int col) {
        final int lineSize = lineSizeInDirection(1, row, col, curr, DECREMENT, INCREMENT);
        if (lineSize >= LINE_SIZE_TO_WIN) {
            return true;
        }
        return lineSizeInDirection(lineSize, row, col, curr, INCREMENT, DECREMENT) >= LINE_SIZE_TO_WIN;
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

        final int parity = maximisingPlayer == player ? 1 : -1;
        int bestVal = -parity * (int) Math.pow(10, LINE_SIZE_TO_WIN + 1);
        for (int col = 0; col < _width; col++) {
            if (board.isValidMove(col)) {
                final Board clonedBoard = board.cloneBoard();
                clonedBoard.makeMove(player, col);
                final boolean winningMove = clonedBoard.isWinningMove(player, col);
                final int eval;
                if (winningMove) {
                    eval = parity * (int) (Math.pow(10, LINE_SIZE_TO_WIN) + lookAheadCount);
                }
                else if (lookAheadCount <= 1) {
                    eval = parity * clonedBoard.boardStrengthForPlayer(player);
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

