package com.example.parisa.ovgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    private WebView infoWebView;
    private String content = "<h1>Information</h1>\n" +         //content of the info page

            "        <p>The OVGO app is a smart parking application which is made as a M.Sc. project " +
            "         at University of Magdeburg.\n" +
            "         This application is designed to inform staff and students about the status of parking lots at campus" +
            "         and is further expandable to more parking zones.\n" +
            "         <p><b>Authors: Parisa Ghalamdansazdameshghi,\n Poorvi Mandyam Bhoolokam.</b></p>\n";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init webView
        infoWebView = view.findViewById(R.id.simpleWebView);
        // displaying text in WebView
        infoWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);//loading the page with content
        //using webView.
    }
}
