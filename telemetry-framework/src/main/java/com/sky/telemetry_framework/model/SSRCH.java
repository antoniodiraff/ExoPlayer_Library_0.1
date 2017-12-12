package com.sky.telemetry_framework.model;

import java.util.ArrayList;


// SSRCH: Session Start Restart CHannel

public class SSRCH extends LiveCH {
    String restartSec;

    public SSRCH() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(originalSessionId);
        payload.add(startTime);
        payload.add(drmTime);
        payload.add(bufferingTime);
        payload.add(playbackStartTime);
        payload.add(ipServer);
        payload.add(manifestUri);
        payload.add(manifestDwnlByte);
        payload.add(manifestDwnlTime);
        payload.add(httpResponse);
        payload.add(channelId);
        payload.add(channelType);
        payload.add(channelEpg);
        payload.add(channelName);
        payload.add(restartSec);
    }

    public String getRestartSec() {
        return restartSec;
    }

    public void setRestartSec(String restartSec) {
        this.restartSec = restartSec;
    }
}
