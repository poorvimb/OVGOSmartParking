package com.example.parisa.ovgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class ParkingLotActivity extends AppCompatActivity {


    ImageButton imageButton1;
    TextView textView1;


    ArrayList<Data> parkingData = new ArrayList<>();
    public boolean flag;

    String data;

    /*
    Displays  a Toast message when a parking lot is selected.
    Tags are used to distinguish between buttons and to further
    expand the usage of app when new sensors added.
     */


    public void parkingLotStatus(View view) {
        String message;
        String pressedButton = view.getTag().toString();
        if (pressedButton.equals("1")) {
            if (flag) {
                message = "The parking lot is free!";

            } else {
                message = "Parking is Full";

            }
        } else {
            message = "This lot is inactive";
        }


        Toast.makeText(ParkingLotActivity.this, message, Toast.LENGTH_SHORT).show();

    }


    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ParkingLotActivity.this);
        builder.setCancelable(true);
        builder.setTitle("All parking is full");
        builder.setMessage("Do you want to get notified?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);

        imageButton1 = findViewById(R.id.imageButton1);
        textView1 = findViewById(R.id.textView1);

        /*If the parking status becomes free/occupied the car icon will change
        this implementation is because we just have one active sensor. It can be
        changed to fit many parking spaces but for now we will show others as
        inactive by reducing the alpha in the car icon.
         */



        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getData();

        } else{
            Toast.makeText(getApplicationContext(),"There is no network connection",Toast.LENGTH_SHORT).show();
        }


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
            if (data.equals("AA==")) {
                flag = true;//parking is empty (AA==) in Base64 format.
                imageButton1.setImageResource(R.drawable.cargreen);
                textView1.setText("\nLot 1\nFree");

            } else if(data.equals("AQ==")) {
                flag = false;//parking is full (AQ==) in Base64 format.
                imageButton1.setImageResource(R.drawable.carred);
                textView1.setText("\nLot 1\nFull");
                createDialog();
            }
            Log.i("flag", String.valueOf(flag));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
    Loads the data from google spreadsheet each time activity opens
    and gets the last parking status.
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
