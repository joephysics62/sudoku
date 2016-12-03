package com.example.joe.connect4app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.joe.connect4app.game.Player;

public class BoardView extends ImageView {

    private final Paint _redPlayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint _yellowPlayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint _gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static int HEIGHT = 6;
    private static int WIDTH = 7;

    private Player[][] _grid = new Player[HEIGHT][WIDTH];

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

    public boolean makeMove(int col, Player player) {
        if (col < WIDTH) {
            int row = rowEntry(col);
            if (row < HEIGHT) {
                _grid[row][col] = player;
                return true;
            }
        }
        return false;
    }

    private int rowEntry(int col) {
        for (int i = 0; i < HEIGHT; i++) {
            if(_grid[i][col] == null) {
                return i;
            }
        }
        return HEIGHT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int margin = 15;
        int pieceSize = 30;
        int cellSize = 70;

        // verticals
        for (int col = 0; col <= WIDTH; col++) {
            int startX = margin + col * cellSize;
            canvas.drawLine(startX, margin, startX, margin + HEIGHT * cellSize, _gridPaint);
        }

        // horizontals
        for (int row = 0; row <= HEIGHT; row++) {
            int startY = margin + row * cellSize;
            canvas.drawLine(margin, startY, margin + WIDTH * cellSize, startY, _gridPaint);
        }

        for (int row = 0; row < HEIGHT; row++) {
            int y = margin + cellSize / 2 + row * cellSize;
            for (int col = 0; col < WIDTH; col++) {
                Player player = _grid[HEIGHT - 1 - row][col];
                if (player != null) {
                    int x = margin + cellSize / 2 + col * cellSize;
                    canvas.drawCircle(x, y, pieceSize, Player.RED == player ? _redPlayerPaint : _yellowPlayerPaint);
                }
            }
        }
    }
}
