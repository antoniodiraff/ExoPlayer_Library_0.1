package com.google.android.exoplayer2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

//(Session sTreaming Pause)

public class STP extends Streaming {
    String pause_time;

    public STP() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(pause_time);

    }

    public String getPause_time() {
        return pause_time;
    }

    public void setPause_time(String pause_time) {
        this.pause_time = pause_time;
    }
}
