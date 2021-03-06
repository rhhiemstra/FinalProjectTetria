package com.example.robert.finalprojecttetria;

import android.graphics.Color;
import java.util.Random;

public class Shapes {
    int currentColor, prevRandomNumber;
    String currentShape = "";
    Random rand = new Random();
    int[][] currentShapeArr = null;
//--------Gets a random shape from the methods below.
    public int[][] getShape(int width){

        int randNum =generateRandom(prevRandomNumber);
        prevRandomNumber = randNum;

        switch (randNum){
            case 0:
                currentShapeArr = square(width);
                currentColor = Color.RED;
                currentShape = "square";
                break;
            case 1:
                currentShapeArr = theLShape(width);
                currentColor = Color.BLUE;
                currentShape = "lShape";
                break;
            case 2:
                currentShapeArr = theLineShape(width);
                currentColor = Color.GREEN;
                currentShape = "lineShape";
                break;
            case 3:
                currentShapeArr = theSnakeShape(width);
                currentColor = Color.YELLOW;
                currentShape = "snakeShape";
                break;
            case 4:
                currentShapeArr = theTeeShape(width);
                currentColor = Color.MAGENTA;
                currentShape = "teeShape";
                break;

        }
        return currentShapeArr;

    }
//---------Used to pass the shape of the class.
    public String getShape(){
        return currentShape;
    }

// holds the starting coordinates of the shapes and assigns them to a multidimensional array.
    public int[][] square(int width){

        int[] bitShape1X = {width/2};
        int[] bitShape1Y = {0};
        int[] bitShpae2X = {width/2+50};
        int[] bitSHape2Y = {50};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {0};
        int[] bitSHape4X = {width/2};
        int[] bitSHape4Y = {50};
        int[][] cord = {bitShape1X, bitShape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }
    public int[][] theLShape(int width){

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
    public int[][] theLineShape(int width){

        int[] bitShape1X = {width/2};
        int[] bitSHape1Y = {0};
        int[] bitShpae2X = {width/2-50};
        int[] bitSHape2Y = {0};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {0};
        int[] bitSHape4X = {width/2-100};
        int[] bitSHape4Y = {0};
        int[][] cord = {bitShape1X, bitSHape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }
    public int[][] theSnakeShape(int width){

        int[] bitShape1X = {width/2};
        int[] bitSHape1Y = {0};
        int[] bitShpae2X = {width/2};
        int[] bitSHape2Y = {50};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {50};
        int[] bitSHape4X = {width/2+50};
        int[] bitSHape4Y = {100};
        int[][] cord = {bitShape1X, bitSHape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }
    public int[][] theTeeShape(int width){

        int[] bitShape1X = {width/2};
        int[] bitSHape1Y = {0};
        int[] bitShpae2X = {width/2-50};
        int[] bitSHape2Y = {0};
        int[] bitSHape3X = {width/2 + 50};
        int[] bitSHape3Y = {0};
        int[] bitSHape4X = {width/2};
        int[] bitSHape4Y = {50};
        int[][] cord = {bitShape1X, bitSHape1Y, bitShpae2X, bitSHape2Y, bitSHape3X, bitSHape3Y, bitSHape4X, bitSHape4Y};
        return cord;




    }
    public int getColor(){
        return currentColor;
    }

//--------Makes sure you don't get the same shape twice in a row.
    int generateRandom(int lastRandomNumber) {

        // add-and-wrap another random number to produce a guaranteed
        // different result.
        // note the one-less-than UPPER_BOUND input
        int rotate = 1 + rand.nextInt(5 - 1);
        // 'rotate' the last number
        return (lastRandomNumber + rotate) % 5;

    }

}



