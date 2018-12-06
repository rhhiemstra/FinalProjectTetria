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
    int[] bitShapeXCordsArr = new int[4];
    int[] bitShapeYCordsArr = new int[4];
    int[][] bitShapeXandYCordsArr = new int[4][2];


    int backgroundColor;
    int squareColor;
    boolean flag = true;

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


                for (int i = 0; i <= bitShapeXCordsArr.length - 1; i++) {
                    if (bitShapeXCordsArr[i] <= 35) {
                        bitShapeXCordsArr[i] = 35;
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

                for (int i = 0; i <= bitShapeXCordsArr.length - 1; i++) {
                    if (bitShapeXCordsArr[i] >= 800) {
                        bitShapeXCordsArr[i] = 800;
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
                    for (int i = 0; i < bitShapeYCordsArr.length; i++) {
                        score.setText(String.valueOf(bitShapeYCordsArr[i]) + " " + String.valueOf(tempWellArrayY.get(i))
                                + " \n" + bitShapeXCordsArr[i] + " " + tempWellArrayX.get(i));
                    }
                }




                board.setImageBitmap(bitmap);

                canvas = new Canvas(bitmap);
                //score.setText(String.valueOf(tempWellArrayX.get(0)) +" " + String.valueOf(tempWellArrayX.get(0)) );


                canvas.drawColor(Color.parseColor("#a9a9a9"));
                int index = tempWellArrayX.size();



                int prevY = y;
                y += 50;

                flag = true;
                for (int i = 0; i <= 3; i++) {
                    for (int a=0; a<tempWellArrayX.size();a++) {


                        if (!tempWellArrayX.isEmpty() && flag && (bitShapeXandYCordsArr[i][0] == tempWellArrayX.get(a)) && bitShapeXandYCordsArr[i][1] + 50 == tempWellArrayY.get(a)) {

                            //bitShapeYCordsArr[i] = prevY;
                            //y = prevY - 50;
                            for (int l = 0; l <= 3; l++) {
                                tempWellArrayX.add(bitShapeXCordsArr[l]);
                                tempWellArrayY.add(bitShapeYCordsArr[l]);
                                tempBitShape.eraseColor(shape.getColor());
                                bitmapArray.add(tempBitShape);

                            }
                            cords = shape.getShape(board.getWidth());

                            y = 0;
                            flag = false;
                        }



                        }
                    if (bitShapeYCordsArr[i] >= canvas.getHeight() - 50) {
                        bitShapeYCordsArr[i] = canvas.getHeight() - 50;
                        y = prevY - 50;
                        for (int l = 0; l <= 3; l++) {
                            tempWellArrayX.add(bitShapeXCordsArr[l]);
                            tempWellArrayY.add(bitShapeYCordsArr[l]);
                            tempBitShape.eraseColor(shape.getColor());
                            bitmapArray.add(tempBitShape);

                        }
                        cords = shape.getShape(board.getWidth());

                        y = 0;
                    }


                }
                if (!tempWellArrayX.isEmpty()) {
                    for (int j = 0; j <= index - 1; j++) {
                        bitmapArray.get(j).eraseColor(shape.getColor());
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
                    bitShapeXCordsArr[i] = cords[i + i][0] + x;
                }
                for (int i = 0; i <= 3; i++) {
                    bitShapeYCordsArr[i] = cords[i + i + 1][0] + y;
                }for (int i = 0; i <= 3; i++) {

                    bitShapeXandYCordsArr[i][0] = cords[i + i][0] + x;
                    bitShapeXandYCordsArr[i][1] = cords[i + i + 1][0] + y;

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
