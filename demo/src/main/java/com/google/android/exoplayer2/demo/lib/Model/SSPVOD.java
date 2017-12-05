package com.google.android.exoplayer2.demo.lib.Model;


// SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

import java.util.ArrayList;

public class SSPVOD extends VOD {
    String delayTimeSec;
    String originalSessionId;


    public SSPVOD() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(originalSessionId);
        payload.add(playbackStartTime);
        payload.add(offerId);
        payload.add(assetTitle);
        payload.add(assetSource);
        payload.add(delayTimeSec);
    }

    public String getDelayTimeSec() {
        return delayTimeSec;
    }

    public void setDelayTimeSec(String delayTimeSec) {
        this.delayTimeSec = delayTimeSec;
    }
    public String getOriginalSessionId() {
        return originalSessionId;
    }

    public void setOriginalSessionId(String originalSessionId) {
        this.originalSessionId = originalSessionId;
    }

}
