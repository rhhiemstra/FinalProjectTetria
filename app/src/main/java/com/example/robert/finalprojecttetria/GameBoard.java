package com.example.robert.finalprojecttetria;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameBoard extends AppCompatActivity {
    ImageView board, nextPiece;
    View view, touchViewLeft, touchViewRight;

    Canvas canvas, nextPieceCanvas;
    Paint paint = new Paint();
    Bitmap bitmap, bitMapNextPiece;

    ArrayList<Bitmap> bitmapArray;
    ArrayList<Integer> bitShapeColorArr;
    TextView score;

    int backgroundColor, position, squareColor, boardWidth, prevY;
    int[] bitShapeXCordsArr = new int[4], bitShapeYCordsArr = new int[4];
    int[][] bitShapeXandYCordsArr = new int[4][2];
    int[][] wellArray;
    int[][] cords, nextCords;
    int x, y = 50;
    int prevX = x;
    int counter = 0;
    int[] deleteRow;
    int bitShapeLeft1, bitShapeLeft2, bitShapeLeft3, bitShapeLeft4,
            bitShapeTop1, bitShapeTop2, bitShapeTop3, bitShapeTop4,
            x1, x2, x3, x4, y1, y2, y3, y4 = 0, currColor, nextColor,
            nextBitShapeLeft1, nextBitShapeLeft2, nextBitShapeLeft3, nextBitShapeLeft4,
            nextBitShapeTop1, nextBitShapeTop2, nextBitShapeTop3, nextBitShapeTop4;

    String currentShape;

    boolean flag = true;
    boolean addColor = false;
    boolean newShape = false;
    boolean eraseLines = false;

    Shapes shape;


    ArrayList<Integer> tempWellArrayX = new ArrayList<>(), tempWellArrayY = new ArrayList<>();
    ArrayList<Integer> deleteRowIndex = new ArrayList<>();
    Button leftBtn, rightBtn;


    CountDownTimer timer;

    Bitmap bitShape1 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape2 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape3 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap bitShape4 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap tempBitShape = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap nextBitShape1 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap nextBitShape2 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap nextBitShape3 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    Bitmap nextBitShape4 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        //----------Initializes all the views and variables----------------------------------------
        onCreateInit();

        //--------Listeners for touch views that move the pieces left or right---------------------
        touchViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnLeftWall();
            }
        });
        touchViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnRightWall();
            }
        });

        //-------Listeners for the rotate buttons which will rotate the shapes left or right-------
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate("right", shape);
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate("leftBtn", shape);
            }
        });

        board.post(new Runnable() {
            @Override
            public void run() {
                drawSomething(board);
            }
        });

    }

    public void drawSomething(View view) {

        final int width = view.getWidth();
        int height = view.getHeight();

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitMapNextPiece = Bitmap.createBitmap(nextPiece.getWidth(), nextPiece.getHeight(), Bitmap.Config.ARGB_8888);
        shape = new Shapes();
        nextCords = shape.getShape(board.getWidth());
        nextColor = shape.getColor();
        cords = nextCords.clone();
        currColor = nextColor;

        timer = new CountDownTimer(2000, 150) {
            @Override
            public void onTick(long millisUntilFinished) {

                //Initializing the Game board and canvas we will be drawing on-------
                nextPiece.setImageBitmap(bitMapNextPiece);
                board.setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);
                nextPieceCanvas = new Canvas(bitMapNextPiece);
                canvas.drawColor(Color.parseColor("#a9a9a9"));
                nextPieceCanvas.drawColor(Color.parseColor("#a9a9a9"));


                //This is where all the collision checks are done.-------------------
                mainGameCheckLoop();

                //This is where we constantly redraw everything on screen.-----------
                mainDrawMethod();

            }

            @Override
            public void onFinish() {
                timer.start();
            }
        }.start();

    }

    public void checkLine() {

        for (int i = 0; i < tempWellArrayY.size(); i++) {

            counter = Collections.frequency(tempWellArrayY, tempWellArrayY.get(i));
            int check = tempWellArrayY.get(i);
            if (counter >= 18) {
            for (int j = 0; j < tempWellArrayY.size(); j++) {
                if (tempWellArrayY.get(j).equals(check)) {
                    int id = j;
                    deleteRowIndex.add(id);
                }
            }

            for (int k = deleteRowIndex.size()-1; k >=0; k--) {
                int remove = deleteRowIndex.get(k);
                    tempWellArrayY.remove(remove);
                    tempWellArrayX.remove(remove);



                }
                eraseLines = true;
            }

        }
        deleteRowIndex.clear();


        System.out.println(counter);












        System.out.println("DELETE LINE!!!!!!!!!" + " Counter = " + counter);
//        for (int i = 0; i < tempWellArrayX.size(); i++) {
//            //if()
//            for (int j = 0; j < wellArray.length; j++) {
//                tempWellArrayX.remove(wellArray[i][1]);
//            }
//        }
    }



    public void onCreateInit(){
        board = findViewById(R.id.gameBoard);
        touchViewLeft = findViewById(R.id.touchViewLeft);
        touchViewRight = findViewById(R.id.touchViewRight);
        score = findViewById(R.id.scoreView);
        leftBtn = findViewById(R.id.left);
        rightBtn = findViewById(R.id.right);
        nextPiece = findViewById(R.id.nextPiece);


        bitmapArray = new ArrayList<Bitmap>();
        bitShapeColorArr = new ArrayList<>();

        boardWidth = board.getWidth();

        backgroundColor = Color.WHITE;
        squareColor = Color.BLACK;
    }

    public void mainGameCheckLoop(){

        int index = tempWellArrayX.size();
        prevY = y;
        y += 50;
        flag = true;

        for (int i = 0; i <= 3; i++) {
            for (int a = 0; a < tempWellArrayX.size(); a++) {
                if (!tempWellArrayX.isEmpty() && flag && (collisionOnXCBool(i , a) && collisionOnYCBool(i, a))) {

                    checkCollision(i, shape);
                    checkLine();


//                    cords = shape.getShape(board.getWidth());
                }
            }
            if (bitShapeYCordsArr[i] >= canvas.getHeight() - 50 && flag) {
                checkBottom(i, shape);
                checkLine();
                //cords = nextCords.clone();
                //newShape = true;
            }
        }
        if (!tempWellArrayX.isEmpty()) {
            index = tempWellArrayX.size();
            reDrawWell(index);

        }
    }
    public void mainDrawMethod(){

        bitShape1.eraseColor(currColor);
        bitShape2.eraseColor(currColor);
        bitShape3.eraseColor(currColor);
        bitShape4.eraseColor(currColor);

        nextBitShape1.eraseColor(currColor);
        nextBitShape2.eraseColor(currColor);
        nextBitShape3.eraseColor(currColor);
        nextBitShape4.eraseColor(currColor);


        bitShapeLeft1 = cords[0][0] + x + x1;
        bitShapeLeft2 = cords[2][0] + x + x2;
        bitShapeLeft3 = cords[4][0] + x + x3;
        bitShapeLeft4 = cords[6][0] + x + x4;

        bitShapeTop1 = cords[1][0] + y + y1;
        bitShapeTop2 = cords[3][0] + y + y2;
        bitShapeTop3 = cords[5][0] + y + y3;
        bitShapeTop4 = cords[7][0] + y + y4;

        nextBitShapeLeft1 = nextCords[0][0]/5;
        nextBitShapeLeft2 = nextCords[2][0]/5;
        nextBitShapeLeft3 = nextCords[4][0]/5 ;
        nextBitShapeLeft4 = nextCords[6][0]/5;

        nextBitShapeTop1 = nextCords[1][0]/5;
        nextBitShapeTop2 = nextCords[3][0]/5;
        nextBitShapeTop3 = nextCords[5][0]/5;
        nextBitShapeTop4 = nextCords[7][0]/5;




        int[] tempXarr = {x1,x2,x3,x4};
        int[] tempYarr = {y1,y2,y3,y4};


        for (int i = 0; i <= 3; i++) {
            bitShapeXCordsArr[i] = cords[i + i][0] + x + tempXarr[i];
        }
        for (int i = 0; i <= 3; i++) {
            bitShapeYCordsArr[i] = cords[i + i + 1][0] + y + tempYarr[i];
        }
        for (int i = 0; i <= 3; i++) {

            bitShapeXandYCordsArr[i][0] = cords[i + i][0] + x + tempXarr[i];
            bitShapeXandYCordsArr[i][1] = cords[i + i + 1][0] + y + tempYarr[i];
            //bitShapeColorArr.add(shape.getColor());

        }


        canvas.drawBitmap(bitShape1, bitShapeLeft1, bitShapeTop1, paint);
        canvas.drawBitmap(bitShape2, bitShapeLeft2, bitShapeTop2, paint);
        canvas.drawBitmap(bitShape3, bitShapeLeft3, bitShapeTop3, paint);
        canvas.drawBitmap(bitShape4, bitShapeLeft4, bitShapeTop4, paint);

        nextPieceCanvas.drawBitmap(nextBitShape1, nextBitShapeLeft1, nextBitShapeTop1, paint);
        nextPieceCanvas.drawBitmap(nextBitShape2, nextBitShapeLeft2, nextBitShapeTop2, paint);
        nextPieceCanvas.drawBitmap(nextBitShape3, nextBitShapeLeft3, nextBitShapeTop3, paint);
        nextPieceCanvas.drawBitmap(nextBitShape3, nextBitShapeLeft3, nextBitShapeTop3, paint);
    }

    public void checkCollision(int i, Shapes shape){

        addColor = true;
        if (addColor) {
            for (int g = 0; g <= 3; g++) {
                bitShapeColorArr.add(shape.getColor());
                position = 0;
                x1 = 0;
                x2 = 0;
                x3 = 0;
                x4 = 0;
                y1 = 0;
                y2 = 0;
                y3 = 0;
                y4 = 0;
            }
            addColor = false;
        }
        for (int l = 0; l <= 3; l++) {
            tempWellArrayX.add(bitShapeXCordsArr[l]);
            tempWellArrayY.add(bitShapeYCordsArr[l]);
            bitmapArray.add(tempBitShape);


        }

        newShape = true;
        if(newShape){
            nextCords = shape.getShape(board.getWidth());
            nextColor = shape.getColor();
            newShape = false;
        }
        cords = nextCords.clone();
        currColor = nextColor;

        x = boardWidth/2;
        y = 0;
        flag = false;
    }
    public void checkBottom(int i, Shapes shape){

        addColor = true;
        if (addColor) {
            for (int g = 0; g <= 3; g++) {
                bitShapeColorArr.add(shape.getColor());
                position = 0;

                y = 0;
                x1 = 0;
                x2 = 0;
                x3 = 0;
                x4 = 0;
                y1 = 0;
                y2 = 0;
                y3 = 0;
                y4 = 0;
            }
            addColor = false;
        }
        bitShapeYCordsArr[i] = canvas.getHeight() - 50;
        y = prevY - 50;
        for (int l = 0; l <= 3; l++) {
            tempWellArrayX.add(bitShapeXCordsArr[l]);
            tempWellArrayY.add(bitShapeYCordsArr[l]);
            bitmapArray.add(tempBitShape);
        }
        newShape = true;
        if(newShape){
            nextCords = shape.getShape(board.getWidth());
            nextColor = shape.getColor();
            newShape = false;
        }
        cords = nextCords.clone();
        currColor = nextColor;
        x = boardWidth/2;
        y=0;
        flag = false;
    }

    public void checkOnRightWall(){
        prevX = x;
        x += 50;

        for (int i = 0; i <= bitShapeXCordsArr.length - 1; i++) {
            if (bitShapeXCordsArr[i] >= 800) {
                bitShapeXCordsArr[i] = 800;
                x = prevX;
            }
        }

    }
    public void checkOnLeftWall(){
        prevX = x;
        x -= 50;
        for (int i = 0; i <= bitShapeXCordsArr.length - 1; i++) {
            if (bitShapeXCordsArr[i] <= 35) {
                bitShapeXCordsArr[i] = 35;
                x = prevX;
            }
        }
    }

    public boolean collisionOnYCBool(int i, int a){
        if(bitShapeXandYCordsArr[i][1] + 50 == tempWellArrayY.get(a)){
            return true;
        } else {
            return false;
        }
    }
    public boolean collisionOnXCBool(int i, int a){
        if(bitShapeXandYCordsArr[i][0] == tempWellArrayX.get(a)){
            return true;
        } else {
            return false;
        }
    }

    public void reDrawWell(int index){
        wellArray = new int[index][2];

        for (int i = 0; i <= index-1; i++) {

            if(eraseLines){
                tempWellArrayY.set(i, tempWellArrayY.get(i)+50);


            }
            wellArray[i][0] = tempWellArrayX.get(i);
            wellArray[i][1] = tempWellArrayY.get(i);

            //bitShapeColorArr.add(shape.getColor());

        }
        for (int j = 0; j <= index - 1; j++) {


            bitmapArray.get(j).eraseColor(bitShapeColorArr.get(j));

            canvas.drawBitmap(bitmapArray.get(j), wellArray[j][0], wellArray[j][1], paint);


        }
        eraseLines = false;

    }

    public void rotate(String direction, Shapes shape) {
        currentShape = shape.getShape();

        switch (currentShape) {

            case "teeShape":
                switch (position) {
                    case 0:
                        if (direction =="leftBtn") {
                            x1 += -50; //x1
                            y1+= 50; //y1

                            //x2
                            y2+= 100; //y2

                            x3+= -100; //x3
                            //y3

                            position = 1;

                        }
                        if (direction =="right") {
                            x1 += 50;
                            y1 += 50;

                            x2 += 100;

                            y3 +=100;

                            position = 3;
                        }
                        break;


                    case 1:
                        if (direction =="leftBtn") {
                            x1 += 50; //x1
                            y1 += 50; //y1

                            x2 += 100; //x2
                            //cords[3][0] +=     //y2

                            //cords[4][0] +=  //x3
                            y3 += 100;    //y3

                            position = 2;
                        }
                        if (direction =="right") {
                            x1 += 50;
                            y1 += -50;

                            //cords[2][0] +=
                            y2 += -100;

                            x3 += 100;
                            //cord[5][] +=

                            position = 0;
                        }
                        break;

                    case 2:
                        if (direction =="leftBtn") {
                            x1 += 50; //x1
                            y1 += -50; //y1

                            //cords[2][0] +=    //x2
                            y2 += -100;    //y2

                            x3 += 100;    //x3
                            //	cords[5][0] += 	//y3

                            position = 3;
                        }
                        if (direction =="right") {
                            x1 += -50;
                            y1 += -50;

                            x2 += -100;
                            //cords[4][0] +=

                            //	cords[4][0] +=
                            y3 += -100;

                            position = 1;
                        }
                        break;
                    case 3:
                        if (direction =="leftBtn") {
                            x1 += -50; //x1
                            y1+= -50; //y1

                            x2+= -100;   //x2
                            //cords[3][0] +=     //y2

                            //cords[4][0] +=   //x3
                            y3 += -100;    //y3

                            position = 0;
                        }
                        if (direction =="right") {
                            x1 += -50;
                            y1 += 50;

                            //cords[2][0] +=
                            y2 += 100;

                            x3 += -100;
                            //cord[5][0] +=

                            position = 2;
                        }
                        break;


                }
                break;

            case "lineShape":
                switch (position) {
                    case 0:
                        if (direction == "leftBtn") {
                            //x1 += -50; //x1
                            //y1+= 50; //y1

                            x2 += 50;
                            y2 += -50; //y2

                            x3 += -50; //x3
                            y3 += 50;

                            x4 += 100;
                            y4 += 100;

                            position = 1;

                        }
                        if (direction == "right") {
                            //x1 += -50; //x1
                            //y1+= 50; //y1

                            x2 += 50;
                            y2 += -50; //y2

                            x3 += -50; //x3
                            y3 += 50;

                            x4 += 100;
                            y4 += 100;

                            position = 1;
                        }
                        break;


                    case 1:
                        if (direction == "leftBtn") {
                            //x1 += -50; //x1
                            //y1+= 50; //y1

                            x2 += -50;
                            y2 += 50; //y2

                            x3 += 50; //x3
                            y3 += -50;

                            x4 += -100;
                            y4 += -100;

                            position = 0;
                        }


                    if (direction =="right") {
                        //x1 += -50; //x1
                        //y1+= 50; //y1

                        x2 += -50;
                        y2 += 50; //y2

                        x3 += 50; //x3
                        y3 += -50;

                        x4 += -100;
                        y4 += -100;

                        position = 0;
                    }
                    break;



                }
                break;

            case "snakeShape":
                switch (position) {
                    case 0:
                        if (direction =="leftBtn") {
                            x1 += -50;
                            y1 += 50;

                            //x2
                            //y2

                            x3 += -50;
                            y3 += -50;

                            //x4 +=
                            y4 += -100;

                            position = 1;

                        }
                        if (direction =="right") {
                            x1 += -50;
                            y1 += 50;

                            //x2
                            //y2

                            x3 += -50;
                            y3 += -50;

                            //x4 +=
                            y4 += -100;

                            position = 1;
                        }
                        break;


                    case 1:
                        if (direction =="leftBtn") {
                            x1 += 50;
                            y1 += -50;

                            //x2
                            //y2

                            x3 += 50;
                            y3 += 50;

                            //x4 +=
                            y4 += 100;
                            position = 0;
                        }
                        if (direction =="right") {
                            x1 += 50;
                            y1 += -50;

                            //x2
                            //y2

                            x3 += 50;
                            y3 += 50;

                            //x4 +=
                            y4 += 100;
                            position = 0;
                        }
                        break;
                }
                break;

            case "lShape":
                switch (position) {
                    case 0:
                        if (direction =="leftBtn") {
                            x1 += -50; //x1
                            y1+= 50; //y1

                            //x2
                            //y2

                            x3+= -100; //x3
                            //y3

                            x4 += 50;
                            y4 += -50;

                            position = 1;

                        }
                        if (direction =="right") {
                            x1 += 50;
                            y1 += 50;

                           // x2 += 100;

                            y3 += 100;

                            x4 += -50;
                            y4 += -50;

                            position = 3;
                        }
                        break;


                    case 1:
                        if (direction =="leftBtn") {
                            x1 += 50; //x1
                            y1 += 50; //y1

                            //x2
                            //cords[3][0] +=     //y2

                            //cords[4][0] +=  //x3
                            y3 += 100;    //y3

                            x4 += -50;
                            y4 += -50;

                            position = 2;
                        }
                        if (direction =="right") {
                            x1 += 50;
                            y1 += -50;

                            //cords[2][0] +=
                            //y2 += 100;

                            x3 += 100;
                            //cord[5][0] +=

                            x4 += -50;
                            y4 += 50;

                            position = 0;
                        }
                        break;

                    case 2:
                        if (direction =="leftBtn") {
                            x1 += 50; //x1
                            y1 += -50; //y1

                            //cords[2][0] +=    //x2
                            //y2

                            x3 += 100;    //x3
                            //	cords[5][0] += 	//y3

                            x4 += -50;
                            y4 += 50;

                            position = 3;
                        }
                        if (direction =="right") {
                            x1 += -50;
                            y1 += -50;

                            //x2 += -100;
                            //cords[4][0] +=

                            //	cords[4][0] +=
                            y3 += -100;

                            x4 += 50;
                            y4 += 50;

                            position = 1;
                        }
                        break;
                    case 3:
                        if (direction =="leftBtn") {
                            x1 += -50; //x1
                            y1+= -50; //y1

                            //x2
                            //cords[3][0] +=     //y2

                            //cords[4][0] +=   //x3
                            y3 += -100;    //y3

                            x4 += 50;
                            y4 += 50;
                            position = 0;
                        }
                        if (direction =="right") {


                            x1 += -50;
                            y1 += 50;

                            //cords[2][0] +=
                            //y2 += -100;

                            x3 += -100;
                            //cord[5][] +=

                            x4 += 50;
                            y4 += -50;
                            position = 2;
                        }
                        break;


                }
                break;
        }




    }
}
