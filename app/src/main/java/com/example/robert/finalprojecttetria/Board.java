package com.example.robert.finalprojecttetria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by GATEWAY PC on 11/6/2018.
 */

public class Board extends View {
    private Color[][] well;
    Bitmap bitShape1 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888);
    Bitmap bitShape2 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );
    Bitmap bitShape3 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );
    Bitmap bitShape4 = Bitmap.createBitmap(50, 50,  Bitmap.Config.ARGB_8888 );

    BitmapDrawable bitmapDrawable = new BitmapDrawable();

    ImageView bitMapImg;
    int top = 0;










    private Rect rectangle;
    private Paint paint;


    public Board(Context context){
        super(context);
        init(context);

    }
    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Board(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        int x = 50;
        int y = 50;
        int sideLength = 200;


        rectangle = new Rect();



        rectangle = new Rect();
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }



    protected void onDraw(final Canvas canvas){
        canvas.drawColor(Color.parseColor("#87CEFA"));
        paint.setColor(Color.BLACK);

        bitShape1.eraseColor(Color.RED);
        bitShape2.eraseColor(Color.RED);
        bitShape3.eraseColor(Color.RED);
        bitShape4.eraseColor(Color.RED);

        canvas.drawBitmap(bitShape1, canvas.getWidth()/2, 0, paint);
        canvas.drawBitmap(bitShape2,(canvas.getWidth()/2),100, paint);
        canvas.drawBitmap(bitShape3,(canvas.getWidth()/2)+50,0, paint);
        canvas.drawBitmap(bitShape4,(canvas.getWidth()/2),50, paint);

//        timer = new CountDownTimer(2000, 50) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                invalidate();
//                top += 40;
//                canvas.drawBitmap(bitShape1, canvas.getWidth()/2, top, paint);
//            }
//
//            @Override
//            public void onFinish() {
//                timer.start();
//
//            }
//        }.start();







//        int[][] rect1 = shapes.square();
//        int[][] rect2 = shapes.square();
//        int[][] rect3 = shapes.square();
//        int[][] rect4 = shapes.square();
//        Rect rectRect = new Rect();
//
//
//        int x = rect1[0][0];
//        int y = rect1[0][1];
//        int top = rect1[0][2];
//        int botttom = rect1[0][3];
//        rectRect.contains(x , y, top, botttom);
//        canvas.drawRect(rectRect, paint);
//        x = rect2[1][0];
//        y = rect2[1][1];
//        top = rect2[1][2];
//        botttom = rect2[1][3];
//        canvas.drawRect(x , y, top, botttom, paint);


    }
}
