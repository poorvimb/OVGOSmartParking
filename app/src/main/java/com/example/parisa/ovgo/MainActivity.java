package com.example.parisa.ovgo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

/*
Main activity consists of a single logo splash screen with a handler.
It waits 3000 milli seconds and jump to the Maps activity.
 */

public class MainActivity extends AppCompatActivity {
    Button startButton;
    private DrawerLayout drawer;

    //private static int SPLASH_TIME_OUT = 3000;

    public void goToMap(View view){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);


        //configurations for drawer layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (drawer.isDrawerOpen(GravityCompat.START)){
            startButton.setVisibility(View.INVISIBLE);
        }else{
            startButton.setVisibility(View.VISIBLE);
        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }

    //Configurations for drawer layout when pressed again.
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
