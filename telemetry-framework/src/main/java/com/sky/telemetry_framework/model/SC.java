package com.sky.telemetry_framework.model;

import java.util.ArrayList;

//        SC : Session Close

public class SC extends Close {
    String closing_time;


    public SC() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(closing_time);
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

}
