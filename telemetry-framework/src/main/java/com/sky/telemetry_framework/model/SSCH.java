package com.sky.telemetry_framework.model;

import java.util.ArrayList;

public class SSCH extends LiveCH {


    public SSCH() {
    }

    @Override
    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(startTime);
        payload.add(drmTime);
        payload.add(3, bufferingTime);
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
        //return payload;
    }


}
