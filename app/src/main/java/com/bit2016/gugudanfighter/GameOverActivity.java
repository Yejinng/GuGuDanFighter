package com.bit2016.gugudanfighter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        int correct = intent.getIntExtra("correct", 0);
        int total = intent.getIntExtra("total",0);

        String result = correct  + "/" + total;
        TextView textView = (TextView)findViewById(R.id.textView_result);
        textView.setText(result);

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);

                startActivity(intent);
            }
        });
    }
}
