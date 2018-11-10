package com.example.robert.finalprojecttetria;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by GATEWAY PC on 11/6/2018.
 */

public class Board extends View {

    private Rect rectangle;
    private Paint paint;

    public Board(Context context){
        super(context);
        int x = 50;
        int y = 50;
        int sideLength = 200;

        rectangle = new Rect(x,y,sideLength, sideLength);

        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(rectangle, paint);
    }
}
