package com.example.robert.finalprojecttetria;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard extends AppCompatActivity {
    ImageView board;
    Canvas canvas;
    Paint paint = new Paint();
    Bitmap bitmap;
    Rect rect = new Rect();
    Rect background = new Rect();
    View view;
    View touchViewLeft;
    View touchViewRight;
    ArrayList<Bitmap> bitmapArray;
    TextView score;
    int[] bitShapeLeftArr = new int[4];
    int[] bitShapToptArr = new int[4];


    int backgroundColor;
    int squareColor;

    int x = 50;
    int y = 50;
    int prevX = x;
    int tempLeft, tempTop = 0;
    int boardWidth;
    int bitShapLeft1 = 0;
    int bitShapLeft2 = 0;
    int bitShapLeft3 = 0;
    int bitShapLeft4 = 0;
    int bitShpaeTop1, bitShpaeTop2, bitShpaeTop3, bitShpaeTop4 = 0;
    ArrayList<Integer> tempWellArrayX = new ArrayList<>();
    ArrayList<Integer> tempWellArrayY = new ArrayList<>();
    ArrayList<Integer> finalWellArrayX = new ArrayList<>();
    ArrayList<Integer> finalWellArrayY = new ArrayList<>();


    CountDownTimer timer;

    Bitmap bitShape1 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape2 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape3 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape4 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap tempBitShape = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        board = findViewById(R.id.gameBoard);
        bitmapArray = new ArrayList<Bitmap>();

        touchViewLeft = findViewById(R.id.touchViewLeft);
        touchViewRight = findViewById(R.id.touchViewRight);
        score = findViewById(R.id.scoreView);

        boardWidth = board.getWidth();


        touchViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevX = x;
                x -= 50;


                for (int i = 0; i <= bitShapeLeftArr.length - 1; i++) {
                    if (bitShapeLeftArr[i] <= 35) {
                        bitShapeLeftArr[i] = 35;
                        x = prevX;
                    }
                }

//                if(bitShapLeft1 <= 35 || bitShapLeft2 <= 35   || bitShapLeft3 <= 35 ||bitShapLeft4 <= 35)
//                    x=-400;
            }
        });

        touchViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevX = x;
                x += 50;

                for (int i = 0; i <= bitShapeLeftArr.length - 1; i++) {
                    if (bitShapeLeftArr[i] >= 800) {
                        bitShapeLeftArr[i] = 800;
                        x = prevX;
                    }
                }
//                if (bitShapLeft1 >= 800 || bitShapLeft2 >= 800 || bitShapLeft3 >= 800 || bitShapLeft4 >= 800)
//                    x = 400 - 50;


            }
        });

        board.post(new Runnable() {
            @Override
            public void run() {
                drawSomething(board);


            }
        });

        backgroundColor = Color.WHITE;
        squareColor = Color.BLACK;


    }

    public void drawSomething(View view) {

        int width = view.getWidth();
        int height = view.getHeight();


        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        timer = new CountDownTimer(2000, 150) {
            Shapes shape = new Shapes(board.getWidth());


            int[][] cords = shape.getShape(board.getWidth());

            @Override
            public void onTick(long millisUntilFinished) {

                if(!tempWellArrayX.isEmpty()){
                    for (int i = 0; i < bitShapToptArr.length;i++) {
                        score.setText(String.valueOf(bitShapToptArr[i]) + " " + String.valueOf(tempWellArrayY.get(i))
                                + " \n" + bitShapeLeftArr[i] + " " + tempWellArrayX.get(i));
                    }
                }




                board.setImageBitmap(bitmap);

                canvas = new Canvas(bitmap);
                //score.setText(String.valueOf(tempWellArrayX.get(0)) +" " + String.valueOf(tempWellArrayX.get(0)) );


                canvas.drawColor(Color.parseColor("#a9a9a9"));
                int index = tempWellArrayX.size();



                int prevY = y;
                y += 50;

                for (int i = 0; i <= 3; i++) {


                    if (!tempWellArrayX.isEmpty() && (tempWellArrayX.contains(bitShapeLeftArr[i]) && tempWellArrayY.contains(bitShapToptArr[i]))) {

                        //bitShapToptArr[i] = prevY;
                        //y = prevY - 50;
                        for (int l = 0; l <= 3; l++) {
                            tempWellArrayX.add(bitShapeLeftArr[l]);
                            if (tempWellArrayY.contains(bitShapToptArr[l])) {
                                tempWellArrayY.add(bitShapToptArr[l] - 50);
                            }else
                                {tempWellArrayY.add(bitShapToptArr[l]);
                            }
                            tempBitShape.eraseColor(Color.GREEN);
                            bitmapArray.add(tempBitShape);

                        }
                        cords = shape.getShape(board.getWidth());


                        y = 0;

                    }


                    else if(bitShapToptArr[i] >= canvas.getHeight() - 50) {
                        bitShapToptArr[i] = canvas.getHeight() - 50;
                        y = prevY - 50;
                        for (int l = 0; l <= 3; l++) {
                            tempWellArrayX.add(bitShapeLeftArr[l]);
                            tempWellArrayY.add(bitShapToptArr[l]);
                            tempBitShape.eraseColor(Color.GREEN);
                            bitmapArray.add(tempBitShape);

                        }

                        cords = shape.getShape(board.getWidth());

                        y = 0;
                    }

                }
                if (!tempWellArrayX.isEmpty()) {
                    for (int j = 0; j <= index - 1; j++) {
                        bitmapArray.get(j).eraseColor(Color.GREEN);
                        canvas.drawBitmap(bitmapArray.get(j), tempWellArrayX.get(j), tempWellArrayY.get(j), paint);
                    }


                }


                bitShape1.eraseColor(Color.RED);
                bitShape2.eraseColor(Color.RED);
                bitShape3.eraseColor(Color.RED);
                bitShape4.eraseColor(Color.RED);

                bitShapLeft1 = cords[0][0] + x;
                bitShapLeft2 = cords[2][0] + x;
                bitShapLeft3 = cords[4][0] + x;
                bitShapLeft4 = cords[6][0] + x;

                bitShpaeTop1 = cords[1][0] + y;
                bitShpaeTop2 = cords[3][0] + y;
                bitShpaeTop3 = cords[5][0] + y;
                bitShpaeTop4 = cords[7][0] + y;


                for (int i = 0; i <= 3; i++) {
                    bitShapeLeftArr[i] = cords[i + i][0] + x;
                }
                for (int i = 0; i <= 3; i++) {
                    bitShapToptArr[i] = cords[i + i + 1][0] + y;
                }


                canvas.drawBitmap(bitShape1, bitShapLeft1, bitShpaeTop1, paint);
                canvas.drawBitmap(bitShape2, bitShapLeft2, bitShpaeTop2, paint);
                canvas.drawBitmap(bitShape3, bitShapLeft3, bitShpaeTop3, paint);
                canvas.drawBitmap(bitShape4, bitShapLeft4, bitShpaeTop4, paint);


            }

            @Override
            public void onFinish() {
                timer.start();

            }
        }.start();


    }


}
