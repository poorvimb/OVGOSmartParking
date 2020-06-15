package com.example.parisa.ovgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {

    WebView webView;
    String content="<h1>Help</h1>\n" +         //content of the page

            "        <p>The OVGO app is a smart parking application designed for the campus of the OVGU which enables users to find out whether a parking lot is free or not. It is a user-friendly android application. Read below if you need some help to get started.</p>\n" +
            "        <p><b>How to use the app:</b>\n" +
            "        <ul>\n" +
            "        <li>Start screen:" +
            "        <dl>\n" +
            "           <dt>Settings:</dt>\n" +
            "           <dd>- Set the radius from your current location in which you want to see the parking zones. </dd>\n" +
            "           <dt>Let's Go button</dt>\n" +
            "           <dd>- Will take you to the map.</dd>\n" +
            "           </dl></li>\n" +
            "        <li>Map Screen: click on the blue icons which shows each parking zone to see the status of your desired parking slot.</li>\n" +
            "        <li>Parking Screen: Colored car icons are used to determine the vacant-'green' and occupied-'red' parking slot.</li>\n" +
            "        <li>Timer Screen: This will show up when the slots are full. You set a time frame to get notified when a slot becomes vacant again.</li>\n";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init webView
        webView = (WebView) view.findViewById(R.id.simpleWebView);
        // displaying text in WebView
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);//loading the page with content
        //using webView.
    }
}
