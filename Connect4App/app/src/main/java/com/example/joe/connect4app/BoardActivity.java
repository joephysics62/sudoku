package com.example.joe.connect4app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joe.connect4app.game.Board;
import com.example.joe.connect4app.game.Player;

public class BoardActivity extends AppCompatActivity {

    public static final int CELL_SIZE = 70;
    public static final int MARGIN = 15;

    public static final int HEIGHT = 6;
    public static final int WIDTH = 7;

    private final Board _board = new Board(WIDTH, HEIGHT);
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        final BoardView boardView = (BoardView) findViewById(R.id.imageView1);

        boardView.setBoard(_board);

        boardView.setOnTouchListener(new View.OnTouchListener() {

            private void checkGameStatus(Player curr, int column) {
                final TextView textView = (TextView) findViewById(R.id.text);
                if (_board.isWinningMove(curr, column)) {
                    textView.setText(curr + " WINS!");
                    _board.setClosed();
                    boardView.invalidate();
                }
                else if (!_board.hasMovesRemaining()) {
                    textView.setText("A DRAW!");
                    _board.setClosed();
                    boardView.invalidate();
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return true;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (_board.isClosed()) {
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        int[] location = new int[2];
                        view.getLocationOnScreen(location);
                        float screenX = event.getRawX();
                        float screenY = event.getRawY();
                        float viewX = screenX - location[0];
                        float viewY = screenY - location[1];
                        int col = (int) (viewX - MARGIN) / CELL_SIZE;
                        if (viewY > MARGIN + HEIGHT * CELL_SIZE) {
                            return true;
                        }
                        if (_board.isValidMove(col)) {
                            _board.makeMove(Player.RED, col);
                            checkGameStatus(Player.RED, col);
                            boardView.invalidate();

                            Board.ScoreMove compMove = _board.minMax(_board, Player.YELLOW, Player.YELLOW, 4);
                            _board.makeMove(Player.YELLOW, compMove.getMove());
                            checkGameStatus(Player.YELLOW, compMove.getMove());
                            boardView.invalidate();
                        }
                        return true;
                    }
                }
                return false;
            }
        });


    }


    public void restart(View view) {
        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("");
        _board.reset();
        final BoardView boardView = (BoardView) findViewById(R.id.imageView1);
        boardView.invalidate();
        textView.invalidate();
    }
}
