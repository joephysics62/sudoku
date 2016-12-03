package com.example.joe.connect4app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.joe.connect4app.game.Board;
import com.example.joe.connect4app.game.Player;

import static com.example.joe.connect4app.BoardActivity.HEIGHT;
import static com.example.joe.connect4app.BoardActivity.WIDTH;

public class BoardView extends ImageView {

    private final Paint _redPlayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint _yellowPlayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint _gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Board _board;

    public BoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        setWillNotDraw(false);

        _redPlayerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        _redPlayerPaint.setColor(Color.RED);

        _yellowPlayerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        _yellowPlayerPaint.setColor(Color.YELLOW);

        _gridPaint.setStyle(Paint.Style.STROKE);
        _gridPaint.setStrokeWidth(2);
        _gridPaint.setColor(Color.BLACK);
    }

    public void setBoard(Board board) {
        _board = board;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int pieceSize = 30;

        // verticals
        for (int col = 0; col <= WIDTH; col++) {
            int startX = BoardActivity.MARGIN + col * BoardActivity.CELL_SIZE;
            canvas.drawLine(startX, BoardActivity.MARGIN, startX, BoardActivity.MARGIN + HEIGHT * BoardActivity.CELL_SIZE, _gridPaint);
        }

        // horizontals
        for (int row = 0; row <= HEIGHT; row++) {
            int startY = BoardActivity.MARGIN + row * BoardActivity.CELL_SIZE;
            canvas.drawLine(BoardActivity.MARGIN, startY, BoardActivity.MARGIN + WIDTH * BoardActivity.CELL_SIZE, startY, _gridPaint);
        }

        for (int row = 0; row < HEIGHT; row++) {
            int y = BoardActivity.MARGIN + BoardActivity.CELL_SIZE / 2 + row * BoardActivity.CELL_SIZE;
            for (int col = 0; col < WIDTH; col++) {
                Player player = _board.playerAt(HEIGHT - 1 - row, col);
                if (player != null) {
                    int x = BoardActivity.MARGIN + BoardActivity.CELL_SIZE / 2 + col * BoardActivity.CELL_SIZE;
                    canvas.drawCircle(x, y, pieceSize, Player.RED == player ? _redPlayerPaint : _yellowPlayerPaint);
                }
            }
        }
    }
}
