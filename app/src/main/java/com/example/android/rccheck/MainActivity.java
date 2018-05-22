package com.example.android.rccheck;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {TextView timer ;
    int n=0;
    MediaPlayer mediaPlayer;
    private TextView time;
    private CountDownTimer timeri;
    ConstraintLayout start;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView)findViewById(R.id.tvtimer);
        start = (ConstraintLayout) findViewById(R.id.btstart);
        time = findViewById(R.id.time);
        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n++;
                if (n==1)start();
                if (n==2) {
                    cancel();
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                }
                if (n==3){
                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);

                }

            }
        });
    }
    private void start(){
        time.setText("15:00");
        timeri = new CountDownTimer(15 * 1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(""+(int)(millisUntilFinished/1000) + ":" +"" + ((int)(millisUntilFinished%1000)/10));
                if ((int)(millisUntilFinished/1000)<=2 ){
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.beep);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onFinish() {
                time.setText("00:00");
                n++;
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        };
        timeri.start();
    }

    private void cancel(){
        if (timeri!=null){
            timeri.cancel();
            timeri =null;
        }
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText(String.format("%02d", Minutes) +":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}
