package com.example.parisa.ovgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    WebView infoWebView;
    String content = "<h1>Help</h1>\n" +         //content of the page

            "        <p>The OVGO app is a smart parking application which is made as a project at University of Magdeburg. This application is made to </p>\n" +
            "        <p><b>App Features list items:</b>\n" +
            "        <ul>\n" +
            "        <li>User can view whether a parking slot is free or not</li>\n" +
            "        <li>Colors are used to determine the vacant-'green' and occupied-'red' parking slot</li>\n" +
            "        <li>The user can also set a time after which he/she needs to be notified when the parking slots are full</li>\n" +
            "        <li>The notification is sent to the users phone in the form of a message   \n";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init webView
        infoWebView = (WebView) view.findViewById(R.id.simpleWebView);
        // displaying text in WebView
        infoWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);//loading the page with content
        //using webView.
    }
}
