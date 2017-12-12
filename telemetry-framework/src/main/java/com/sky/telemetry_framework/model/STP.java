package com.sky.telemetry_framework.model;

import java.util.ArrayList;

//(Session sTreaming Pause)

public class STP extends Streaming {
    String pauseTime;

    public STP() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(pauseTime);

    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }
}
