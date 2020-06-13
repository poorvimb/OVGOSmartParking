package com.example.parisa.ovgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    TextView timerText;
    SeekBar timerSeekBar;
    Boolean counterActive = false;
    Button button;
    CountDownTimer counter;

    public void resetTimer(){
        button.setText("Notify Me!");
        timerSeekBar.setProgress(60);
        timerSeekBar.setEnabled(true);
        timerText.setText("1:00");
        counter.cancel();
        counterActive = false;
    }


    public void start(View view) {
        if (counterActive) {
            resetTimer();

        } else {
            counterActive = true;
            timerSeekBar.setEnabled(false);
            button.setText("Stop!");
            counter = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    resetTimer();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"ID")
                            .setSmallIcon(R.drawable.map)
                            .setContentTitle("OVGO App")
                            .setContentText("Time is up.The requested lot is still full!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());

                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        final int minutes = secondsLeft/60;
        final int seconds = secondsLeft-(minutes*60);
        String secondString = Integer.toString(seconds);

        if(seconds <= 9){
            secondString = "0" + secondString;
        }

        timerText.setText(minutes+ " : "+ secondString);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        button = findViewById(R.id.button);

        timerText = findViewById(R.id.timerText);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(1800); //it refers to maximum of 30 minutes.
        timerSeekBar.setProgress(60); //the seek bar starts with 1 minute
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }
}
