package com.sky.telemetry_framework.model;

import java.util.ArrayList;

public abstract class ApplicationEvents {

    public ArrayList<String> payload;

    public ArrayList<String> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<String> payload) {
        this.payload = payload;
    }


}
