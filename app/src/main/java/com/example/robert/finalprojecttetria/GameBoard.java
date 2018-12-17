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
    Button pause;
    Boolean pauseFlag = false;
    int scoreInt = 0;
    int[] bitShapeXCordsArr = new int[4], bitShapeYCordsArr = new int[4], deleteRow;
    int[][] bitShapeXandYCordsArr = new int[4][2], wellArray,cords, nextCords;
    int bitShapeLeft1, bitShapeLeft2, bitShapeLeft3, bitShapeLeft4, bitShapeTop1, bitShapeTop2, bitShapeTop3, bitShapeTop4, x1, x2, x3, x4, y1, y2, y3, y4 = 0,
            currColor, nextColor, nextBitShapeLeft1, nextBitShapeLeft2, nextBitShapeLeft3, nextBitShapeLeft4, nextBitShapeTop1, nextBitShapeTop2, nextBitShapeTop3,
            nextBitShapeTop4, x, y = 50, prevX = x, counter = 0, backgroundColor, position, squareColor, boardWidth, prevY;
    String currentShape;
    boolean flag = true, addColor = false, newShape = false, eraseLines = false;
    Shapes shape;
    ArrayList<Integer> tempWellArrayX = new ArrayList<>(), tempWellArrayY = new ArrayList<>(), deleteRowIndex = new ArrayList<>();
    Button leftBtn, rightBtn;
    CountDownTimer timer;

    Bitmap bitShape1 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888), bitShape2 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888),
            bitShape3 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888), bitShape4 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888),
            tempBitShape = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888), nextBitShape1 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888),
            nextBitShape2 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888), nextBitShape3 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888),
            nextBitShape4 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        score = findViewById(R.id.scoreView);
        pause = findViewById(R.id.pauseBtn);

        score.setText(String.valueOf(scoreInt));

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
        //This truly starts the game.
        board.post(new Runnable() {
            @Override
            public void run() {
                drawSomething(board);
            }
        });

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
//This is the main method that everything else runs inside of.
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

                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!pauseFlag){
                            timer.cancel();
                            pauseFlag = true;
                        }else{
                            timer.start();
                            pauseFlag = false;
                        }
                    }
                });


                //Initializing the Game board and canvas we will be drawing on-------
                nextPiece.setImageBitmap(bitMapNextPiece);
                board.setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);
                nextPieceCanvas = new Canvas(bitMapNextPiece);
                canvas.drawColor(Color.parseColor("#a9a9a9"));
                nextPieceCanvas.drawColor(Color.parseColor("#a9a9a9"));

                //This is where all the collision checks are looped through.-------------------
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
    //This is the loop that runs to constantly check if there has been a collision.
    public void mainGameCheckLoop(){
        int index;
        prevY = y;
        y += 50;
        flag = true;
        for (int i = 0; i <= 3; i++) {
            for (int a = 0; a < tempWellArrayX.size(); a++) {
                //This loop is to see if one piece has touched another piece.
                if (!tempWellArrayX.isEmpty() && flag && (collisionOnXCBool(i , a) && collisionOnYCBool(i, a))) {

                    checkCollision(i, shape);
                    checkLine();

                }
            }
            //This a loop that constantly checks if a piece has hit the bottom of the screen.
            if (bitShapeYCordsArr[i] >= canvas.getHeight() - 50 && flag) {
                checkBottom(i, shape);
                checkLine();
            }
        }
        //This is used constantly call to redraw the well.
        if (!tempWellArrayX.isEmpty()) {
            index = tempWellArrayX.size();
            reDrawWell(index);
        }
    }
    //This is used to draw the falling piece.
    public void mainDrawMethod(){
        //This sets the colors of the current pieces falling.
        bitShape1.eraseColor(currColor);
        bitShape2.eraseColor(currColor);
        bitShape3.eraseColor(currColor);
        bitShape4.eraseColor(currColor);


        //This is used to update the X coordinate of the falling piece.
        bitShapeLeft1 = cords[0][0] + x + x1;
        bitShapeLeft2 = cords[2][0] + x + x2;
        bitShapeLeft3 = cords[4][0] + x + x3;
        bitShapeLeft4 = cords[6][0] + x + x4;
        //This is used to update the Y coordinate of the falling piece.
        bitShapeTop1 = cords[1][0] + y + y1;
        bitShapeTop2 = cords[3][0] + y + y2;
        bitShapeTop3 = cords[5][0] + y + y3;
        bitShapeTop4 = cords[7][0] + y + y4;

        int[] tempXarr = {x1,x2,x3,x4};
        int[] tempYarr = {y1,y2,y3,y4};

        //These are filling arrays with the current coordinates to check for collisions and to redraw the falling piece.
        for (int i = 0; i <= 3; i++) {
            bitShapeXCordsArr[i] = cords[i + i][0] + x + tempXarr[i];
        }
        for (int i = 0; i <= 3; i++) {
            bitShapeYCordsArr[i] = cords[i + i + 1][0] + y + tempYarr[i];
        }
        for (int i = 0; i <= 3; i++) {
            bitShapeXandYCordsArr[i][0] = cords[i + i][0] + x + tempXarr[i];
            bitShapeXandYCordsArr[i][1] = cords[i + i + 1][0] + y + tempYarr[i];
        }

        //This redraws the falling pieces constantly.
        canvas.drawBitmap(bitShape1, bitShapeLeft1, bitShapeTop1, paint);
        canvas.drawBitmap(bitShape2, bitShapeLeft2, bitShapeTop2, paint);
        canvas.drawBitmap(bitShape3, bitShapeLeft3, bitShapeTop3, paint);
        canvas.drawBitmap(bitShape4, bitShapeLeft4, bitShapeTop4, paint);

    }
    //The method is called if one piece has connected with another.
    public void checkCollision(int i, Shapes shape){
        //This adds the color of the current shape to an array of all the colors in the well and is used to redraw the well.
        addColor = true;
        if (addColor) {
            for (int g = 0; g <= 3; g++) {
                bitShapeColorArr.add(shape.getColor());
                position = 0;
                x1 = 0; x2 = 0; x3 = 0; x4 = 0;
                y1 = 0; y2 = 0; y3 = 0; y4 = 0;
            }
            addColor = false;
        }
        //This adds the shape to well arrays.
        for (int l = 0; l <= 3; l++) {
            tempWellArrayX.add(bitShapeXCordsArr[l]);
            tempWellArrayY.add(bitShapeYCordsArr[l]);
            bitmapArray.add(tempBitShape);
        }
        //This gets the next shape and assigns the values to the arrays used to draw the shape.
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
    //This method is called if a piece hits the bottom.
    public void checkBottom(int i, Shapes shape){
        //This adds the color of the current shape to an array of all the colors in the well and is used to redraw the well.
        addColor = true;
        if (addColor) {
            for (int g = 0; g <= 3; g++) {
                bitShapeColorArr.add(shape.getColor());
                position = 0;
                y = 0;
                x1 = 0; x2 = 0; x3 = 0; x4 = 0;
                y1 = 0; y2 = 0; y3 = 0; y4 = 0;
            }
            addColor = false;
        }
        //This is used to bump the piece back up from below the board and store those coordinates.
        bitShapeYCordsArr[i] = canvas.getHeight() - 50;
        y = prevY - 50;
        for (int l = 0; l <= 3; l++) {
            tempWellArrayX.add(bitShapeXCordsArr[l]);
            tempWellArrayY.add(bitShapeYCordsArr[l]);
            bitmapArray.add(tempBitShape);
        }
        //This is to create a new shape.
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
    //This method is to remove all the pieces of a complete line and move the remains pieces down.
    public void checkLine() {

        for (int i = 0; i < tempWellArrayY.size(); i++) {
            //We did the math to find out that the screen will hold 18 single bitshapes on a single line.
            //This counts how many times the same Y values appears in the Y array.
            //If it is greater then or equal to 18 we remove the pieces by storing the index of each piece and looping through it for the x, y and color arrays.
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
                    bitShapeColorArr.remove(remove);



                }
                eraseLines = true;
                deleteRowIndex.clear();
                scoreInt += 50;
                score.setText(String.valueOf(scoreInt));

            }
        }
        deleteRowIndex.clear();
    }
    //When pressing left or right these make sure the pieces does not go off the screen.
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
    //These bool methods were created to make the if statement they are used in look cleaner.
    //They compare the the array of the current piece cords with the arrays of the well cords to see if the equal each other.
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
    //This method is used to redraw the entire well of fallen pieces.
    public void reDrawWell(int index){
        wellArray = new int[index][2];

        //This is used to set the new pieces to the entire well and redraw them.
        for (int i = 0; i <= index-1; i++) {
            //This is how we moves the remaining pieces down after we have cleared a line.
            if(eraseLines){
                tempWellArrayY.set(i, tempWellArrayY.get(i)+50);

            }
            wellArray[i][0] = tempWellArrayX.get(i);
            wellArray[i][1] = tempWellArrayY.get(i);
        }
        for (int j = 0; j <= index - 1; j++) {
            bitmapArray.get(j).eraseColor(bitShapeColorArr.get(j));
            canvas.drawBitmap(bitmapArray.get(j), wellArray[j][0], wellArray[j][1], paint);
        }

        eraseLines = false;

    }
    //This is how we rotate the pieces. It is extremely convoluted and complicated.
    //We had to sit at a table with excel open on one laptop and this program on another just to visually see where the pieces would be.
    //There has to be another way we could have done this. I mean this has if statements in side switch statement that are themselves in another switch statement.
    //But it works so don't ever touch it.
    public void rotate(String direction, Shapes shape) {
        currentShape = shape.getShape();

        switch (currentShape) {
            //To try to make this simple as can be f the first switch is used to determine what shape we have.
            //The second is used to determine what position the shapes is currently in.
            //Then the if statements are used to see if we need to rotate left or right.
            //.....man.

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
    //The answer to life greatest question is 42.
}
