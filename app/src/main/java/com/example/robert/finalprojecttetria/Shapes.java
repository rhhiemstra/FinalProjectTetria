package com.example.robert.finalprojecttetria;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.constraint.solver.widgets.Rectangle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class Shapes {




    Shapes(int width){

    }

    public int[][] getShape(int width){
        Random rand = new Random();
        int randNum = rand.nextInt(7);
        int[][] randShape = null;

        switch (randNum){
            case 0:
                randShape = square(width);
                break;
            case 1:
                randShape = theLShape(width);
                break;
            case 2:
                randShape = square(width);
                break;
            case 3:
                randShape = square(width);
                break;
            case 4:
                randShape = square(width);
                break;
            case 5:
                randShape = square(width);
                break;
            case 6:
                randShape = square(width);
                break;
        }
        return randShape;

    }

    public int[][] square(int width){
//        canvas.drawBitmap(bitShape1, canvas.getWidth()/2+x, 0+y, paint);
//        canvas.drawBitmap(bitShape2,(canvas.getWidth()/2)+x,100+y, paint);
//        canvas.drawBitmap(bitShape3,(canvas.getWidth()/2)+50+x,0+y, paint);
//        canvas.drawBitmap(bitShape4,(canvas.getWidth()/2)+x,50+y, paint);
        int[] bitShape1X = {width/2};
        int[] bitSHape1Y = {0};
        int[] bitShpae2X = {width/2+50};
        int[] bitSHape2Y = {50};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {0};
        int[] bitSHape4X = {width/2};
        int[] bitSHape4Y = {50};
        int[][] cord = {bitShape1X, bitSHape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }
    public int[][] theLShape(int width){
//        canvas.drawBitmap(bitShape1, canvas.getWidth()/2+x, 0+y, paint);
//        canvas.drawBitmap(bitShape2,(canvas.getWidth()/2)+x,100+y, paint);
//        canvas.drawBitmap(bitShape3,(canvas.getWidth()/2)+50+x,0+y, paint);
//        canvas.drawBitmap(bitShape4,(canvas.getWidth()/2)+x,50+y, paint);
        int[] bitShape1X = {width/2};
        int[] bitSHape1Y = {0};
        int[] bitShpae2X = {width/2};
        int[] bitSHape2Y = {50};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {0};
        int[] bitSHape4X = {width/2};
        int[] bitSHape4Y = {100};
        int[][] cord = {bitShape1X, bitSHape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }



//    Shapes tee(int x, int y){
//
//        fill(255, 0, 255);
//        stroke(0);
//
//        rect(0+x, 0+y, 10, 10);
//        rect(-10+x, 0+y, 10, 10);
//        rect(10+x, 0+y, 10, 10);
//        rect(0+x, 10+y, 10, 10);
//        return this;
//    }
//
//
//    Shapes straight(int x, int y){
//
//        fill(0, 0, 255);
//        stroke(0);
//
//        rect(0+x, 0+y, 10, 10);
//        rect(0+x, -10+y, 10, 10);
//        rect(0+x, -20+y, 10, 10);
//        rect(0+x, 10+y, 10, 10);
//        return this;
//    }
//
//    Shapes lz(int x, int y){
//
//        fill(144, 144, 0);
//        stroke(0);
//
//        rect(0+x, 10+y, 10, 10);
//        rect(-10+x, 10+y, 10, 10);
//        rect(0+x, 0+y, 10, 10);
//        rect(10+x, 0+y, 10, 10);
//        return this;
//    }
//
//    Shapes rz(int x, int y){
//
//        fill(0, 144, 144);
//        stroke(0);
//
//        rect(0+x, 10+y, 10, 10);
//        rect(10+x, 10+y, 10, 10);
//        rect(0+x, 0+y, 10, 10);
//        rect(-10+x, 0+y, 10, 10);
//        return this;
//    }
//
//    Shapes ll(int x, int y){
//        fill(255);
//        stroke(0);
//
//        rect(0+x, 0+y, 10, 10);
//        rect(0+x, -10+y, 10, 10);
//        rect(0+x, -20+y, 10, 10);
//        rect(-10+x, 0+y, 10, 10);
//        return this;
//    }
//
//    Shapes rl(int x, int y){
//        fill(255);
//        stroke(0);
//
//        rect(0+x, 0+y, 10, 10);
//        rect(0+x, -10+y, 10, 10);
//        rect(0+x, -20+y, 10, 10);
//        rect(10+x, 0+y, 10, 10);
//        return this;
//    }






}



