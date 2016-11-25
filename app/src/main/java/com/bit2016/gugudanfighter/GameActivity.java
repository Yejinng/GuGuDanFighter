package com.bit2016.gugudanfighter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TIME_LIMIT = 30;
    private Timer timer = new Timer();
    private TextView tvLastTime =  null;
    private TextView a =  null;
    private TextView b =  null;
    private static final int[] buttons = new int[]{R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };
    private HashSet<Integer> list = new HashSet<>();
    int randomNumber_a = 0;
    int randomNumber_b = 0;
    private int result = 0;
    private int total =0;
    int ok = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvLastTime = (TextView)findViewById(R.id.textView7);
        timer.schedule( new GameTimerTask(), 1000, 1000);
        newQuestion();

    }
    private void updateLastTime( int seconds) {
        tvLastTime.setText( "" +(TIME_LIMIT - seconds) );
    }


    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
        btn.getText();
        if( result == Integer.parseInt(btn.getText().toString())) {
            newQuestion();
            ok++;
            total++;
        } else {
            newQuestion();
            total++;
        }
        TextView all = (TextView)findViewById(R.id.textView_all);
        TextView okey = (TextView)findViewById(R.id.textView_ok);
        all.setText(String.valueOf(total));
        okey.setText(String.valueOf(ok));
    }

    private class GameTimerTask extends TimerTask {
        private int seconds;

        @Override
        public void run() {
            seconds ++;
            if(seconds >= TIME_LIMIT) {
                // 타이머 중지
                timer.cancel();

                Log.i("--------->", "타이머 정지");
                // Toast.makeText( getApplicationContext(), "타이머 중지", Toast.LENGTH_LONG ).show();
                // 결과 activity 호출 startActivity 랑 intent
                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                intent.putExtra("correct", ok);
                intent.putExtra("total", total);
                startActivity(intent);
                //finish();

            }
            // UI 변경
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateLastTime( seconds );
                }
            });
        }
    }

    private void newQuestion() {

        a = (TextView)findViewById(R.id.textView_a);
        b = (TextView)findViewById(R.id.textView_b);
        randomNumber_a = randomNumber();
        randomNumber_b = randomNumber();
        result = randomNumber_a * randomNumber_b;
        a.setText( "" + randomNumber_a );
        b.setText( "" + randomNumber_b );

        generateButton();

    }

    private int randomNumber(){
        int a = 1 + (int)(Math.random() * ((9 - 1) + 1));
        return a;
    }

    private void generateButton() {
        Random r = new Random();
        list = new HashSet<>();
        while(true){
            int data = (r.nextInt(9)+1) * (r.nextInt(9)+1);
            if (list.size() > 9) {
                break;
            }
            list.add(data);
        }
        Iterator iterator = list.iterator();
        for( int i =0; i<9; i++) {
           Button button = (Button) findViewById(buttons[i]);
            if(iterator.hasNext()) {
                button.setText(String.valueOf(iterator.next()));
            }
            button.setOnClickListener(this);
        }

        if( ! list.contains( result )) {
            Button button = (Button)findViewById(buttons[r.nextInt(9)]);
            button.setText( String.valueOf(result) );
        }
    }
}
