package com.example.robert.finalprojecttetria;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button newGameBtn;
    Button continueBtn;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        newGameBtn = findViewById(R.id.newGameBtn);
        continueBtn = findViewById(R.id.continueBtn);
        myIntent = new Intent(this, GameBoard.class);


        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(myIntent);
            }
        });



    }
}
