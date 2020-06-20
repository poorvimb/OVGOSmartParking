package com.example.parisa.ovgo;

public class Data {
    private String payload_raw;


    Data(String payload_raw) {

        this.setPayload(payload_raw);
    }

    private void setPayload(String payload_raw) {
        this.payload_raw = payload_raw;
    }
}
