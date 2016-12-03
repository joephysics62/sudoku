package com.example.joe.connect4app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.joe.connect4app.game.Player;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        final BoardView boardView = (BoardView) findViewById(R.id.imageView1);

        boardView.setOnTouchListener(new View.OnTouchListener() {
            private Player player = Player.RED;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        int col = (int) event.getX() / 90;
                        if (boardView.makeMove(col, player)) {
                            player = player.nextPlayer();
                            boardView.invalidate();
                        }
                        break;
                    }
                }
                return true;
            }
        });


    }
}
