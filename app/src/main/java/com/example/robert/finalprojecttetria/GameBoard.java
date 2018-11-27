package com.example.robert.finalprojecttetria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.zip.Inflater;

public class GameBoard extends AppCompatActivity {
    ImageView board;
    Canvas canvas;
    Paint paint = new Paint();
    Bitmap bitmap;
    Rect rect = new Rect();
    Rect background = new Rect();
    View view;


    int backgroundColor;
    int squareColor;

    int x = 50;
    int y = 50;


    CountDownTimer timer;

    Bitmap bitShape1 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888);
    Bitmap bitShape2 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );
    Bitmap bitShape3 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );
    Bitmap bitShape4 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        board = findViewById(R.id.gameBoard);

      board.post(new Runnable() {
          @Override
          public void run() {
              drawSomething(board);


          }
      });




        backgroundColor = Color.WHITE;
        squareColor = Color.BLACK;







    }

    public void drawSomething(View view){

        int width = view.getWidth();
        int height = view.getHeight();

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);





        timer = new CountDownTimer(2000, 150) {
            Shapes shape = new Shapes(board.getWidth());
            int [][] cords = shape.getShape(board.getWidth());
            @Override
            public void onTick(long millisUntilFinished) {





                board.setImageBitmap(bitmap);

                canvas = new Canvas(bitmap);


                canvas.drawColor(Color.parseColor("#a9a9a9"));

                bitShape1.eraseColor(Color.RED);
                bitShape2.eraseColor(Color.RED);
                bitShape3.eraseColor(Color.RED);
                bitShape4.eraseColor(Color.RED);


                canvas.drawBitmap(bitShape1,cords[0][0]+x, cords[1][0]+y, paint);
                canvas.drawBitmap(bitShape2,cords[2][0]+x, cords[3][0]+y, paint);
                canvas.drawBitmap(bitShape3,cords[4][0]+x, cords[5][0]+y, paint);
                canvas.drawBitmap(bitShape4,cords[6][0]+x, cords[7][0]+y, paint);




                if( y >= canvas.getHeight()-150){
                    y = 0;
                    cords = shape.getShape(board.getWidth());


                }else

                y += 50;
            }

            @Override
            public void onFinish() {
                timer.start();

            }
        }.start();



    }



}
