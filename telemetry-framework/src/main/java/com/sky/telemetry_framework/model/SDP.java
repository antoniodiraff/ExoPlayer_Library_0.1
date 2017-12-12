package com.sky.telemetry_framework.model;

import java.util.ArrayList;

//SDP : Session Download Pause

public class SDP extends Download {
    String pauseTime;
    String pauseCause;
    String downloadPerc;

    public SDP() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(pauseTime);
        payload.add(pauseCause);
        payload.add(downloadPerc);
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getPauseCause() {
        return pauseCause;
    }

    public void setPauseCause(String pauseCause) {
        this.pauseCause = pauseCause;
    }

    public String getDownloadPerc() {
        return downloadPerc;
    }

    public void setDownloadPerc(String downloadPerc) {
        this.downloadPerc = downloadPerc;
    }
}
