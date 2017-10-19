package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;

public class ACI extends ApplicationEvents {

    String stop_time;

    public ACI() {

    }
    public void updateACIPayload() {
        payload = new ArrayList<String>();
        payload.add(stop_time);
    }

    public ACI(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }
}
