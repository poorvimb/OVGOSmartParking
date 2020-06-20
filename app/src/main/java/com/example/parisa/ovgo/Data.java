package com.example.parisa.ovgo;

public class Data {
    private String payload_raw;


    Data(String payload_raw) {

        this.setPayload(payload_raw);
    }

    String getPayload() {
        return payload_raw.substring(payload_raw.length() - 4);
    }

    private void setPayload(String payload_raw) {
        this.payload_raw = payload_raw;

    }
}
