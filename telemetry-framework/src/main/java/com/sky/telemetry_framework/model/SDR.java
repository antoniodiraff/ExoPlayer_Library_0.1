package com.sky.telemetry_framework.model;

import java.util.ArrayList;


// SDR : Session Download Resume

public class SDR extends Download {
    String resumeTime;

    public SDR() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(resumeTime);
    }

    public String getResumeTime() {
        return resumeTime;
    }

    public void setResumeTime(String resumeTime) {
        this.resumeTime = resumeTime;
    }
}
