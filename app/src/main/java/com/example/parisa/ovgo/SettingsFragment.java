package com.example.parisa.ovgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private int distance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    /*
    Defines the seek bar and gets the value from seek bar and send it to MapsActivity
    when button is clicked.
     */
    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.setDistanceButton);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(30);
        final TextView seekBarValue = view.findViewById(R.id.seekbarText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("com.example.parisa.ovgo", Context.MODE_PRIVATE);
                preferences.edit().putInt("distanceFromSettings", distance).apply();
                Toast.makeText(getContext(), "Distance saved", Toast.LENGTH_SHORT).show();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(progress + " KM");
                distance = progress;
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
