package com.example.parisa.ovgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    ArrayList<Data> parkingData = new ArrayList<>();
    public boolean parkingIsFree;

    String data;

    TextView timerText;
    SeekBar timerSeekBar;
    Boolean counterActive = false;
    Button button;
    CountDownTimer counter;

    public void resetTimer() {
        button.setText("Notify Me!");
        timerSeekBar.setProgress(60);
        timerSeekBar.setEnabled(true);
        timerText.setText("1:00");
        counter.cancel();
        counterActive = false;
    }
/*
 Method for the button click. counterActive is a boolean value that shows the counter status.
 If the counter is not active it starts a new counter. Inside the onTick method it will constantly
 check for the parking status change and notifies the user if it becomes free again.
 */

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
                    getData();
                    if (parkingIsFree) {
                        resetTimer();
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ID")
                                .setSmallIcon(R.drawable.map)
                                .setContentTitle("OVGO App")
                                .setContentText("The parking lot is free again.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                        // notificationId is a unique int for each notification that you must define
                        notificationManager.notify(2, builder.build());
                    }

                }

                @Override
                public void onFinish() {

                    resetTimer();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ID")
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

    public void updateTimer(int secondsLeft) {
        final int minutes = secondsLeft / 60;
        final int seconds = secondsLeft - (minutes * 60);
        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerText.setText(minutes + " : " + secondString);

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

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");
            JSONObject row = rows.getJSONObject(rows.length() - 1);
            JSONArray columns = row.getJSONArray("c");
            String payload_raw = columns.getJSONObject(4).getString("v");

            Data dataOfParking = new Data(payload_raw);
            parkingData.add(dataOfParking);
            data = payload_raw;

            /*If the parking status becomes free/occupied the car icon will change
        this implementation. because we just have one active sensor. It can be
        changed to fit many parking spaces but for now we will show others as
        inactive by reducing the alpha in the car icon.
         */
            if (data.equals("AA==")) {
                parkingIsFree = true;//parking is empty (AA==) in Base64 format.


            } else if (data.equals("AQ==")) {
                parkingIsFree = false;//parking is full (AQ==) in Base64 format.

            }
            Log.i("flag", String.valueOf(parkingIsFree));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
    Reloading the data each time it's called.
    This method is called inside the timer onTick method to get data during active timer mode.
     */
    public void getData() {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1gGyJS2phIcmiTEEUdUhOsyqekEudBue_NtNkjKsTQrQ");
    }
}
