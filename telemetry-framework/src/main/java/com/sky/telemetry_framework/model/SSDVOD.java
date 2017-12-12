package com.sky.telemetry_framework.model;


// SSDVOD : Session Start Download VOD


import java.util.ArrayList;

public class SSDVOD extends VOD {

    public SSDVOD(String drmTime,String bufferingTime,String ipServer,String manifestUri,String manifestDwnlByte,
                  String manifestDwnlTime,String httpResponse,String offerId,String assetTitle,String assetSource) {
        this.assetSource=assetSource;
        this.drmTime=drmTime;
        this.bufferingTime=bufferingTime;
        this.manifestUri=manifestUri;
        this.manifestDwnlTime=manifestDwnlTime;
        this.manifestDwnlByte=manifestDwnlByte;
        this.httpResponse=httpResponse;
        this.offerId=offerId;
        this.assetTitle=assetTitle;
        this.ipServer=ipServer;
    }

    public SSDVOD(){

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(startTime);
        payload.add(drmTime);
        payload.add(bufferingTime);
        payload.add(ipServer);
        payload.add(manifestUri);
        payload.add(manifestDwnlByte);
        payload.add(manifestDwnlTime);
        payload.add(httpResponse);
        payload.add(offerId);
        payload.add(assetTitle);
        payload.add(assetSource);
    }
}
