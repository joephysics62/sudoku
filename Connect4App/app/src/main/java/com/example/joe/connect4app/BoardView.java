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

    private final Player[][] _grid;

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

        _grid = new Player[][] {
                {Player.YELLOW, Player.RED, null, null, Player.RED, null, Player.YELLOW},
                {Player.YELLOW, null, null, null, Player.RED, null, null},
                {Player.YELLOW, null, null, null, Player.RED, null, null},
                {Player.RED, null, null, null, Player.YELLOW, null, null},
                {Player.RED, null, null, null, Player.YELLOW, null, null},
                {Player.RED, null, null, null, Player.YELLOW, null, null}
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int margin = 15;
        int pieceSize = 30;
        int cellSize = 70;
        int height = _grid.length;
        int width = _grid[0].length;

        // verticals
        for (int col = 0; col <= width; col++) {
            int startX = margin + col * cellSize;
            canvas.drawLine(startX, margin, startX, margin + height * cellSize, _gridPaint);
        }

        // horizontals
        for (int row = 0; row <= height; row++) {
            int startY = margin + row * cellSize;
            canvas.drawLine(margin, startY, margin + width * cellSize, startY, _gridPaint);
        }

        for (int row = 0; row < height; row++) {
            int y = margin + cellSize / 2 + row * cellSize;
            for (int col = 0; col < width; col++) {
                Player player = _grid[height - 1 - row][col];
                if (player != null) {
                    int x = margin + cellSize / 2 + col * cellSize;
                    canvas.drawCircle(x, y, pieceSize, Player.RED == player ? _redPlayerPaint : _yellowPlayerPaint);
                }
            }
        }
    }
}
