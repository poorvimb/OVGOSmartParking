package com.example.parisa.ovgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

public class ParkingLotActivity extends AppCompatActivity {
    boolean parkingFree;
    String message = "";
    ImageButton imageButton1;



    public void parkingLotStatus(View view){
        String pressedButton = view.getTag().toString();
        if(pressedButton.equals("1")) {
            if (parkingFree) {
                message = "The parking lot is free!";
            } else {
                message = "Parking is Full";
                createDialog();
            }
        } else{
            message = "This lot is inactive";
        }


        Toast.makeText(ParkingLotActivity.this,message,Toast.LENGTH_SHORT).show();

    }


    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParkingLotActivity.this);
        builder.setCancelable(true);
        builder.setTitle("All parking is full");
        builder.setMessage("Do you want to get notified?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),TimerActivity.class);
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

        parkingFree = new Random().nextBoolean();
        imageButton1 = findViewById(R.id.imageButton1);

        /*If the parking status becomes free/occupied the car icon will change
        this implementation is because we just have one active sensor. It can be
        changed to fit many parking spaces but for now we will show others as
        inactive by reducing the alpha in the car icon.
         */
        if (parkingFree){
            imageButton1.setImageResource(R.drawable.cargreen);
        } else{
            imageButton1.setImageResource(R.drawable.carred);
        }

    }
}
